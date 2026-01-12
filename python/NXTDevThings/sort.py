import nxt.locator
import nxt.motor
import nxt.sensor
import nxt.sensor.generic
import nxt.sensor.digital
from time import sleep
import logging

import data

#global vars:
ma = None
mapos = 0
mb = None
mc = None
colorSensor = None

class Ball:
	def __init__(self, color):
		self.color = color

	def getColor(self):
		return self.color

class Tower:
	def __init__(self, position, ball):
		self.position = position
		self.ball = ball

	def getPosition(self):
		return self.position

	def getBall(self):
		return self.ball

	def setBall(self, newBall):
		self.ball = newBall




towers = [
	Tower(-100, Ball("None")),
	Tower(-126, Ball("None")),
	Tower(-141, Ball("None")),
	Tower(-175, Ball("None")),
	Tower(-200, Ball("Unknown"))#need to find out what the color is and place it to a certain tower
]
isHolding = False

def getDistance(num1, num2):
	return abs(num1 - num2)

def calibrate():
	global ma, mb, mc, mapos, isHolding
	#make sure that motor b is holding the arm in the air:
	mb.run(power=15)
	sleep(2)
	mb.idle()
	mb.brake()

	#calibrate the 'hand'

	mc.run(power=-15)
	sleep(2)
	mc.idle()
	mc.brake()
	mc.turn(15, 90)
	isHolding = False

	#finally, calibrate the rotating motor:

	ma.run(power=15)
	sleep(3)
	ma.idle()
	ma.brake()
	ma.reset_position(False)
	mapos = ma.get_tacho().rotation_count
	print("Reseted the position: " + str(mapos))

def goto(position):
	#calibrateRotation()
	global ma
	sleep(0.5)
	distance = getDistance(position, ma.get_tacho().rotation_count)
	print("distance: " + str(distance))
	print(ma.get_tacho().rotation_count)
	print(position)
	if (ma.get_tacho().rotation_count < position):
		ma.turn(15, abs(distance))
		ma.brake()
	else:
		ma.turn(-15, abs(distance))
		ma.brake()

	print("I am at position: " + str(ma.get_tacho().rotation_count))


def grab():
	global mb, mc, isHolding
	if isHolding:
		print("Hold up")
		return
	mb.turn(-15, 90)
	mb.brake()
	mc.turn(15,45)
	mb.turn(15, 90)
	mb.brake()
	isHolding = True

def calibrateRotation():
	global ma
	ma.run(power=15)
	sleep(3)
	ma.idle()
	ma.brake()
	ma.reset_position(False)

def place():
	global mb, mc, isHolding
	if (isHolding == False):
		print("Hold up")
		return
	mb.turn(-15, 90)
	mc.turn(-15,45)
	mb.turn(15, 90)
	isHolding = False

def checkColor(brick):
	global mb, isHolding, colorSensor
	if (isHolding == False):
		print("Hold up")
		return
	mb.turn(-25, 88)
	sleep(1)
	brick.play_tone(700,200)
	print(colorSensor.get_sample())
	if (colorSensor.DetectedColor == "DetectedColor.BLACK"):
		print("Read Error!")
	sleep(1)
	mb.turn(25, 88)

def endProgram():
	global ma, mb, mc
	calibrateRotation()
	#set all motors to idle:
	ma.idle()
	mb.idle()
	mc.idle()

def findUnknownBall():
	unknowns = 0
	array = []
	for t in towers:
		if (t.getBall().getColor() == "Unknown"):
			unknowns += 1
			array.append(t)


	if unknowns == 0:
		print("Could not find any unknown 'balls!' *insert lenny face*")
		return 0
	else:
		return array[0].getPosition()



def simpleMove():
	with nxt.locator.find() as b:
		global ma, mb, mc, colorSensor, tower_1_pos, tower_2_pos, tower_3_pos, tower_4_pos, tower_color_pos
		ma = b.get_motor(nxt.motor.Port.A)
		mb = b.get_motor(nxt.motor.Port.B)
		mc = b.get_motor(nxt.motor.Port.C)
		colorSensor = b.get_sensor(nxt.sensor.Port.S3, nxt.sensor.generic.Color)
		calibrate()



		goto(findUnknownBall())
		grab()
		goto(towers[2].getPosition())
		checkColor(b)
		goto(towers[1].getPosition())
		place()
		endProgram()
		#data.playBadApple()

def resetBot():
	with nxt.locator.find() as b:
		global ma, mb, mc, tower_1_pos, tower_2_pos, tower_3_pos, tower_4_pos, tower_color_pos
		ma = b.get_motor(nxt.motor.Port.A)
		mb = b.get_motor(nxt.motor.Port.B)
		mc = b.get_motor(nxt.motor.Port.C)
		endProgram()
		#data.playBadApple()

def test() :
	with nxt.locator.find() as b:
		ma = b.get_motor(nxt.motor.Port.A)
		ma.turn(75, 970)

if __name__ == "__main__":
	#simpleMove()
	#resetBot()
	test()