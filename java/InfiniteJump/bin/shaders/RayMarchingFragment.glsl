#version 330 core

uniform vec3 iResolution;
uniform float iTime;
uniform vec3 Camera;
uniform vec3 CameraPos;
uniform float iFOV;

out vec4 fragColor;

const int MAX_STEPS = 256;
const float MAX_DIST = 500;
const float EPSILON = 0.001;
float sgn(float x) {
	return (x<0)?-1.0:1.0;
}
vec2 sgn(vec2 v) {
	return vec2((v.x<0)?-1:1, (v.y<0)?-1:1);
}
float vmax(vec2 v) {
	return max(v.x, v.y);
}

float vmax(vec3 v) {
	return max(max(v.x, v.y), v.z);
}

float vmax(vec4 v) {
	return max(max(v.x, v.y), max(v.z, v.w));
}

float vmin(vec2 v) {
	return min(v.x, v.y);
}

float vmin(vec3 v) {
	return min(min(v.x, v.y), v.z);
}

float vmin(vec4 v) {
	return min(min(v.x, v.y), min(v.z, v.w));
}

float pMirror (inout float p, float dist) {
	float s = sgn(p);
	p = abs(p)-dist;
	return s;
}
float fOpUnionChamfer(float a, float b, float r) {
	return min(min(a, b), (a - r + b)*sqrt(0.5));
}
float fOpUnionStairs(float a, float b, float r, float n) {
	float s = r/n;
	float u = b-r;
	return min(min(a,b), 0.5 * (u + a + abs ((mod (u - a + s, 2 * s)) - s)));
}
float pMod1(inout float p, float size) {
	float halfsize = size*0.5;
	float c = floor((p + halfsize)/size);
	p = mod(p + halfsize, size) - halfsize;
	return c;
}
void pR45(inout vec2 p) {
	p = (p + vec2(p.y, -p.x))*sqrt(0.5);
}
vec3 pMod3(inout vec3 p, vec3 size) {
	vec3 c = floor((p + size*0.5)/size);
	p = mod(p + size*0.5, size) - size*0.5;
	return c;
}
float fSphere(vec3 p, float r) {
	return length(p) - r;
}
float fPlane(vec3 p, vec3 n, float distanceFromOrigin) {
	return dot(p, n) + distanceFromOrigin;
}
void pR(inout vec2 p, float a) {
	p = cos(a)*p + sin(a)*vec2(p.y, -p.x);
}
float fOpDifferenceColumns(float a, float b, float r, float n) {
	a = -a;
	float m = min(a, b);
	//avoid the expensive computation where not needed (produces discontinuity though)
	if ((a < r) && (b < r)) {
		vec2 p = vec2(a, b);
		float columnradius = r*sqrt(2)/n/2.0;
		columnradius = r*sqrt(2)/((n-1)*2+sqrt(2));

		pR45(p);
		p.y += columnradius;
		p.x -= sqrt(2)/2*r;
		p.x += -columnradius*sqrt(2)/2;

		if (mod(n,2) == 1) {
			p.y += columnradius;
		}
		pMod1(p.y,columnradius*2);

		float result = -length(p) + columnradius;
		result = max(result, p.x);
		result = min(result, a);
		return -min(result, b);
	} else {
		return -m;
	}
}
float fTorus(vec3 p, float smallRadius, float largeRadius) {
	return length(vec2(length(p.xz) - largeRadius, p.y)) - smallRadius;
}
vec2 pMirrorOctant (inout vec2 p, vec2 dist) {
	vec2 s = sgn(p);
	pMirror(p.x, dist.x);
	pMirror(p.y, dist.y);
	if (p.y > p.x)
		p.xy = p.yx;
	return s;
}
float fBox2Cheap(vec2 p, vec2 b) {
	return vmax(abs(p)-b);
}
float fBoxCheap(vec3 p, vec3 b) { //cheap box
	return vmax(abs(p) - b);
}
float fCylinder(vec3 p, float r, float height) {
	float d = length(p.xz) - r;
	d = max(d, abs(p.y) - height);
	return d;
}


float fDisplace(vec3 p) {
    pR(p.yz, sin(2.0 * iTime));
    return (sin(p.x + 4.0 * iTime) * sin(p.y + sin(2.0 * iTime)) * sin(p.z + 6.0 * iTime));
}

vec2 fOpUnionID(vec2 res1, vec2 res2) {
    return (res1.x < res2.x) ? res1 : res2;
}

vec2 fOpDifferenceID(vec2 res1, vec2 res2) {
    return (res1.x > -res2.x) ? res1 : vec2(-res2.x, res2.y);
}

vec2 fOpDifferenceColumnsID(vec2 res1, vec2 res2, float r, float n) {
    float dist = fOpDifferenceColumns(res1.x, res2.x, r, n);
    return (res1.x > -res2.x) ? vec2(dist, res1.y) : vec2(dist, res2.y);
}

vec2 fOpUnionStairsID(vec2 res1, vec2 res2, float r, float n) {
    float dist = fOpUnionStairs(res1.x, res2.x, r, n);
    return (res1.x < res2.x) ? vec2(dist, res1.y) : vec2(dist, res2.y);
}

vec2 fOpUnionChamferID(vec2 res1, vec2 res2, float r) {
    float dist = fOpUnionChamfer(res1.x, res2.x, r);
    return (res1.x < res2.x) ? vec2(dist, res1.y) : vec2(dist, res2.y);
}

vec2 map(vec3 p) {
    // plane
    float planeDist = fPlane(p, vec3(0, 1, 0), 1.0);
    float planeID = 2.0;
    vec2 plane = vec2(planeDist, planeID);
    float sphereDist = fSphere(p, 1.0);
    float sphereID = 1.0;
    vec2 sphere = vec2(sphereDist, sphereID);
    
    return fOpUnionID(plane, sphere);
}

vec2 rayMarch(vec3 ro, vec3 rd) {
	vec2 hit, object;
	for (int i = 0; i < MAX_STEPS; i++) {
		vec3 p = ro + object.x * rd;
		hit = map(p);
		object.x += hit.x;
		object.y = hit.y;
		if (abs(hit.x) < EPSILON || object.x > MAX_DIST) break;
	}
	return object;
}

vec3 getNormal(vec3 p) {
	vec2 e = vec2(EPSILON, 0.0);
	vec3 n = vec3(map(p).x) - vec3(map(p - e.xyy).x, map(p - e.yxy).x, map(p - e.yyx).x);
	return normalize(n);
}

float getSoftShadow(vec3 p, vec3  lightPos) {
	float res = 1.0;
	float dist = 0.01;
	float lightSize = 0.03;
	for (int i = 0; i < MAX_STEPS; i++) {
		float hit = map(p + lightPos * dist).x;
		res = min(res, hit / (dist * lightSize));
		if (hit < 0.0001 || dist > 60.0) break;
	}
	return clamp(res, 0.0, 1.0);
}

vec3 getLight(vec3 p, vec3 rd, vec3 color) {
    vec3 lightPos = vec3(10.0, 55.0, -20.0);
    vec3 L = normalize(lightPos - p);
    vec3 N = getNormal(p);
    vec3 V = -rd;
    vec3 R = reflect(-L, N);

    vec3 specColor = vec3(0.5);
    vec3 specular = specColor * pow(clamp(dot(R, V), 0.0, 1.0), 10.0);
    vec3 diffuse = color * clamp(dot(L, N), 0.0, 1.0);
    vec3 ambient = color * 0.05;
    vec3 fresnel = 0.25 * color * pow(1.0 + dot(rd, N), 3.0);

    // shadows
    float shadow = getSoftShadow(p + N * 0.02, normalize(lightPos));

    return ambient + fresnel + (specular + diffuse) * shadow;
}

vec3 getMaterial(vec3 p, float id) {
    vec3 m;
    switch (int(id)) {
        case 1:
        m = vec3(0.9, 0.0, 0.0); break;
        case 2:
        float v = 0.2 + 0.4 * mod(floor(p.x*2) + floor(p.z*2), 2);
        if (v > 0.5) m = vec3(1);
        else m = vec3(0);
        break;
    }
    return m;
}

mat3 getCam(vec3 ro, vec3 lookAt) {
    vec3 camF = normalize(vec3(lookAt - ro));
    vec3 camR = normalize(cross(vec3(0, 1, 0), camF));
    vec3 camU = cross(camF, camR);
    return mat3(camR, camU, camF);
}

vec3 render(vec2 uv) {	
	vec3 col = vec3(0);
	vec3 ro = vec3(0.0, 1.0, -3.0);
	vec3 lookAt = vec3(0, 0, 0);
    vec3 rd = getCam(ro, lookAt) * normalize(vec3(uv, iFOV));
	vec2 object = rayMarch(ro, rd);
	
	vec3 background = vec3(0.5, 0.8, 0.9);
	
	if (object.x < MAX_DIST) {
		vec3 p = ro + object.x * rd;
		vec3 material = getMaterial(p, object.y);
		col += getLight(p, rd, material);
		
		col = mix(col, background, 1.0 - exp(-0.0002 * object.x * object.x));
	} else {
        col += background - max(0.9 * rd.y, 0.0);
    }
    return col;
}

vec2 getUV(vec2 offset) {
	return (2.0 * (gl_FragCoord.xy + offset) - iResolution.xy) / iResolution.y;
}

vec3 renderAAx4() {
	vec4 e = vec4(0.125, -0.125, 0.375, -0.375);
	vec3 colAA = render(getUV(e.xz)) + render(getUV(e.yw)) + render(getUV(e.wx)) + render(getUV(e.zy));
	return colAA /= 4.0;
}

void main() {
	
	vec3 col = renderAAx4();
	
	col = pow(col, vec3(0.4545));
	fragColor = vec4(col, 1.0);
}