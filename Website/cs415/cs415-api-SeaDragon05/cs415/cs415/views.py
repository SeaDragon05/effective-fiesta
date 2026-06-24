from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from datetime import datetime, timedelta
import jwt
from django.conf import settings
from django.views.decorators.csrf import csrf_exempt
from django.utils.decorators import method_decorator
from cs415.models import Webuser, Addresstype, Useraddress, Userphone, Phonetype, Pagedata, Userinfo
from cs415.serializers import WebUserSerializer, AddressTypeSerializer, UserAddressTypeSerializer, UserInfoTypeSerializer, UserPhoneTypeSerializer, PhoneTypeTypeSerializer, PageDataTypeSerializer
from cs415.serializers import WebUserSerializerPost, AddressTypeSerializerPost, UserAddressTypeSerializerPost, UserInfoTypeSerializerPost, UserPhoneTypeSerializerPost, PhoneTypeTypeSerializerPost, PageDataTypeSerializerPost
from drf_yasg.utils import swagger_auto_schema
from drf_yasg import openapi

class WebUserAPIView(APIView):
    @swagger_auto_schema(
        operation_description="Get a list of all web users",
        responses={200: WebUserSerializer(many=True)}
    )
    def get(self,request):
        users = Webuser.objects.all()
        serializer = WebUserSerializer(users, many=True)
        return Response(serializer.data)

    @swagger_auto_schema(
        operation_description="Create a new web user",
        request_body=WebUserSerializerPost,
        responses={201: WebUserSerializer()}
    )
    def post(self, request, *args, **kwargs):
        request.data['created_date'] = datetime.now()
        request.data['is_active'] = 1
        serializer = WebUserSerializerPost(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        else:
            return Response({'errors': serializer.errors},
                                status=status.HTTP_400_BAD_REQUEST)
    
class AddressTypeAPIView(APIView):
    @swagger_auto_schema(
        operation_description="Get a list of all address types",
        responses={200: AddressTypeSerializer(many=True)}
    )
    def get(self, request):
        address_types = Addresstype.objects.all()
        serializer = AddressTypeSerializer(address_types, many=True)
        return Response(serializer.data)
    
    @swagger_auto_schema(
        operation_description="Create a new address type",
        request_body=AddressTypeSerializerPost,
        responses={201: AddressTypeSerializer()}
    )
    def post(self, request, *args, **kwargs):
        serializer = AddressTypeSerializerPost(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        else:
            return Response({'errors': serializer.errors},
                                status=status.HTTP_400_BAD_REQUEST)
    
class UserAddressAPIView(APIView):
    @swagger_auto_schema(
        operation_description="Get a list of all user addresses",
        responses={200: UserAddressTypeSerializer(many=True)}
    )
    def get(self, request):
        user_address = Useraddress.objects.all()
        serializer = UserAddressTypeSerializer(user_address, many=True)
        return Response(serializer.data)
    
    @swagger_auto_schema(
        operation_description="Create a new user address",
        request_body=UserAddressTypeSerializerPost,
        responses={201: UserAddressTypeSerializer()}
    )
    def post(self, request, *args, **kwargs):
        request.data['created_date'] = datetime.now()
        serializer = UserAddressTypeSerializerPost(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        else:
            return Response({'errors': serializer.errors},
                                status=status.HTTP_400_BAD_REQUEST)

class UserInfoAPIView(APIView):
    @swagger_auto_schema(
        operation_description="Get a list of all user infos",
        responses={200: UserInfoTypeSerializer(many=True)}
    )
    def get(self, request):
        user_info = Userinfo.objects.all()
        serializer = UserInfoTypeSerializer(user_info, many=True)
        return Response(serializer.data)
    
    @swagger_auto_schema(
        operation_description="Create a new user info",
        request_body=UserInfoTypeSerializerPost,
        responses={201: UserInfoTypeSerializer()}
    )
    def post(self, request, *args, **kwargs):   
        request.data['created_date'] = datetime.now()
        serializer = UserInfoTypeSerializerPost(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        else:
            return Response({'errors': serializer.errors},
                                status=status.HTTP_400_BAD_REQUEST)

class UserPhoneAPIView(APIView):
    @swagger_auto_schema(
        operation_description="Get a list of all user phones",
        responses={200: UserPhoneTypeSerializer(many=True)}
    )
    def get(self, request):
        user_phone = Userphone.objects.all()
        serializer = UserPhoneTypeSerializer(user_phone, many=True)
        return Response(serializer.data)
    
    @swagger_auto_schema(
        operation_description="Create a new user phone",
        request_body=UserPhoneTypeSerializerPost,
        responses={201: UserPhoneTypeSerializer()}
    )
    def post(self, request, *args, **kwargs):        
        request.data['created_date'] = datetime.now()
        serializer = UserPhoneTypeSerializerPost(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        else:
            return Response({'errors': serializer.errors},
                                status=status.HTTP_400_BAD_REQUEST)

class PhoneTypeAPIView(APIView):
    @swagger_auto_schema(
        operation_description="Get a list of all phone types",
        responses={200: PhoneTypeTypeSerializer(many=True)}
    )
    def get(self, request):
        phone_type = Phonetype.objects.all()
        serializer = PhoneTypeTypeSerializer(phone_type, many=True)
        return Response(serializer.data)
    
    @swagger_auto_schema(
        operation_description="Create a new phone type",
        request_body=PhoneTypeTypeSerializerPost,
        responses={201: PhoneTypeTypeSerializer()}
    )
    def post(self, request, *args, **kwargs):
        serializer = PhoneTypeTypeSerializerPost(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        else:
            return Response({'errors': serializer.errors},
                                status=status.HTTP_400_BAD_REQUEST)

class PageDataAPIView(APIView):
    @swagger_auto_schema(
        operation_description="Get a list of all page data",
        responses={200: PageDataTypeSerializer(many=True)}
    )
    def get(self, request):
        page_data = Pagedata.objects.all()
        serializer = PageDataTypeSerializer(page_data, many=True)
        return Response(serializer.data)

    @swagger_auto_schema(
        operation_description="Create a new page data",
        request_body=PageDataTypeSerializerPost,
        responses={201: PageDataTypeSerializer()}      
    )
    def post(self, request, *args, **kwargs):
        request.data['created_date'] = datetime.now()
        serializer = PageDataTypeSerializerPost(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        else:
            return Response({'errors': serializer.errors},
                                status=status.HTTP_400_BAD_REQUEST)
        
class SingleWebUserAPIView(APIView):
    @swagger_auto_schema(
        operation_description="Get a single web user by ID",
        responses={200: WebUserSerializer()}
    )
    def get(self, request, web_user_id):
        user_data = {}
        user = Webuser.objects.get(web_user_id=web_user_id)
        user_serial = WebUserSerializer(user)
        user_data.update({"user": user_serial.data})
        addresses = UserAddressTypeSerializer(Useraddress.objects.filter(web_user=user), many=True)
        user_data.update({"addresses": addresses.data})
        info = UserInfoTypeSerializer(Userinfo.objects.filter(web_user=user), many=True)
        user_data.update({"info": info.data})
        phone = UserPhoneTypeSerializer(Userphone.objects.filter(web_user=user).select_related(), many=True)
        user_data.update({"phones": phone.data})
        return Response(user_data)

    @swagger_auto_schema(
        operation_description="Update a single web user by ID",
        request_body=WebUserSerializerPost,
        responses={200: WebUserSerializer()}
    )
    def patch(self, request, web_user_id):
        webuser_obj = Webuser.objects.get(web_user_id=web_user_id)
        serializer = WebUserSerializer(webuser_obj, data=request.data, partial=True)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response({'errors': serializer.errors},
                            status=status.HTTP_400_BAD_REQUEST)

class SingleAddressTypeAPIView(APIView):
    @swagger_auto_schema(
        operation_description="Get a single address type",
        responses={200: AddressTypeSerializer()}
    )
    def get(self, request, address_type_id):
        address_type = Addresstype.objects.get(address_type_id=address_type_id)
        serializer = AddressTypeSerializer(address_type)
        return Response(serializer.data)

    @swagger_auto_schema(
        operation_description="Update a single address type",
        request_body=AddressTypeSerializerPost,
        responses={200: AddressTypeSerializer()}
    )
    def patch(self, request, address_type_id):
        address_type_obj = Addresstype.objects.get(address_type_id=address_type_id)
        serializer = AddressTypeSerializerPost(address_type_obj, data=request.data, partial=True)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response({'errors': serializer.errors},
                            status=status.HTTP_400_BAD_REQUEST)

class SingleUserAddressAPIView(APIView):
    @swagger_auto_schema(
        operation_description="Get a single user address",
        responses={200: UserAddressTypeSerializer()}
    )
    def get(self, request, user_address_id):
        user_address = Useraddress.objects.get(user_address_id=user_address_id)
        serializer = UserAddressTypeSerializer(user_address)
        return Response(serializer.data)
    
    @swagger_auto_schema(
        operation_description="Update a single user address",
        request_body=UserAddressTypeSerializerPost,
        responses={200: UserAddressTypeSerializer()}
    )
    def patch(self, request, user_address_id):
        user_address_obj = Useraddress.objects.get(user_address_id=user_address_id)
        serializer = UserAddressTypeSerializerPost(user_address_obj, data=request.data, partial=True)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response({'errors': serializer.errors},
                            status=status.HTTP_400_BAD_REQUEST)

class SingleUserInfoAPIView(APIView):
    @swagger_auto_schema(
        operation_description="Get a single user info",
        responses={200: UserInfoTypeSerializer()}
    )
    def get(self, request, user_info_id):
        user_info = Userinfo.objects.get(user_info_id=user_info_id)
        serializer = UserInfoTypeSerializer(user_info)
        return Response(serializer.data)
    
    @swagger_auto_schema(
        operation_description="Update a single user info",
        request_body=UserInfoTypeSerializerPost,
        responses={200: UserInfoTypeSerializer()}
    )
    def patch(self, request, user_info_id):
        user_info_obj = Userinfo.objects.get(user_info_id=user_info_id)
        serializer = UserInfoTypeSerializerPost(user_info_obj, data=request.data, partial=True)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response({'errors': serializer.errors},
                            status=status.HTTP_400_BAD_REQUEST)

class SingleUserPhoneAPIView(APIView):
    @swagger_auto_schema(
        operation_description="Get a single user phone",
        responses={200: UserPhoneTypeSerializer()}
    )
    def get(self, request, user_phone_id):
        user_phone = Userphone.objects.get(user_phone_id=user_phone_id)
        serializer = UserPhoneTypeSerializer(user_phone)
        return Response(serializer.data)
    
    @swagger_auto_schema(
        operation_description="Update a single user phone",
        request_body=UserPhoneTypeSerializerPost,
        responses={200: UserPhoneTypeSerializer()}
    )
    def patch(self, request, user_phone_id):
        user_phone_obj = Userphone.objects.get(user_phone_id=user_phone_id)
        serializer = UserPhoneTypeSerializerPost(user_phone_obj, data=request.data, partial=True)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response({'errors': serializer.errors},
                            status=status.HTTP_400_BAD_REQUEST)

class SinglePhoneTypeAPIView(APIView):
    @swagger_auto_schema(
        operation_description="Get a single phone type",
        responses={200: PhoneTypeTypeSerializer()}
    )
    def get(self, request, phone_type_id):
        phone_type = Phonetype.objects.get(phone_type_id=phone_type_id)
        serializer = PhoneTypeTypeSerializer(phone_type)
        return Response(serializer.data)

    @swagger_auto_schema(
        operation_description="Update a single phone type",
        request_body=PhoneTypeTypeSerializerPost,
        responses={200: PhoneTypeTypeSerializer()}
    )
    def patch(self, request, phone_type_id):
        phone_type_obj = Phonetype.objects.get(phone_type_id=phone_type_id)
        serializer = PhoneTypeTypeSerializerPost(phone_type_obj, data=request.data, partial=True)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response({'errors': serializer.errors},
                            status=status.HTTP_400_BAD_REQUEST)

class SinglePageDataAPIView(APIView):
    @swagger_auto_schema(
        operation_description="Get a single page data",
        responses={200: PageDataTypeSerializer()}
    )
    def get(self, request, page_data_id):
        page_data = Pagedata.objects.get(page_data_id=page_data_id)
        serializer = PageDataTypeSerializer(page_data)
        return Response(serializer.data)

    @swagger_auto_schema(
        operation_description="Update a single page data",
        request_body=PageDataTypeSerializerPost,
        responses={200: PageDataTypeSerializer()}
    )
    def patch(self, request, page_data_id):
        page_data_obj = Pagedata.objects.get(page_data_id=page_data_id)
        serializer = PageDataTypeSerializerPost(page_data_obj, data=request.data, partial=True)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response({'errors': serializer.errors},
                            status=status.HTTP_400_BAD_REQUEST)

class SingleWebUserEmailAPIView(APIView):
    @swagger_auto_schema(
        operation_description="Get a single web user by email",
        responses={200: WebUserSerializer()}
    )
    def get(self, request, email):
        user = Webuser.objects.get(email=email)
        serializer = WebUserSerializer(user)
        return Response(serializer.data)
    
    @swagger_auto_schema(
        operation_description="Update a single web user by email",
        request_body=WebUserSerializerPost,
        responses={200: WebUserSerializer()}
    )
    def patch(self, request, email):
        user_obj = Webuser.objects.get(email=email)
        serializer = WebUserSerializerPost(user_obj, data=request.data, partial=True)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response({'errors': serializer.errors},
                            status=status.HTTP_400_BAD_REQUEST)
    
@method_decorator(csrf_exempt, name='dispatch')
class Login(APIView):
    def post(self, request):
        email = request.data.get("email")
        password = request.data.get("password")

        if not email or not password:
            return Response({'success': False,
                             'error': 'Email and Password must have a value'},
                             status = status.HTTP_400_BAD_REQUEST)

        check_user = Webuser.objects.filter(email=email).exists()
        if check_user == False:
            return Response({'success': False,
                             'error': 'User with this email does not exist'},
                             status=status.HTTP_404_NOT_FOUND)

        check_pass = Webuser.objects.filter(email = email, password=password).exists()
        if check_pass == False:
            return Response({'success': False,
                             'error': 'Incorrect password for user'},
                             status=status.HTTP_401_UNAUTHORIZED)
        user = Webuser.objects.get(email=email, password=password)
        # add last login to User table
        serializer = WebUserSerializer(user, data={'last_login': str(datetime.now())}, partial=True)
        if serializer.is_valid():
            serializer.save()

        if user is not None:
            # Generate JWT token with expiration
            payload = {
                'user_id': user.web_user_id,
                'email': user.email,
                'exp': datetime.utcnow() + timedelta(hours=24),
                'iat': datetime.utcnow()
            }
            jwt_token = jwt.encode(payload, settings.SECRET_KEY, algorithm='HS256')
            return Response({'success': True,
                             'user_id': user.web_user_id,
                             'token': jwt_token},
                             status=status.HTTP_200_OK)
        else:
            return Response({'success': False,
                             'error': 'Invalid Login Credentials'},
                             status=status.HTTP_400_BAD_REQUEST)
