from rest_framework import serializers
from cs415.models import Webuser, Addresstype, Useraddress, Userphone, Phonetype, Pagedata, Userinfo

class WebUserSerializer(serializers.ModelSerializer):
    class Meta:
        model = Webuser
        fields = ['web_user_id', 'first_name', 'last_name', 'email', 'created_date', 'is_active', 'last_login']

class WebUserSerializerPost(serializers.ModelSerializer):
    class Meta:
        model = Webuser
        fields = '__all__'

class AddressTypeSerializer(serializers.ModelSerializer):
    class Meta:
        model = Addresstype
        fields = ['address_type_id', 'address_type']

class AddressTypeSerializerPost(serializers.ModelSerializer):
    class Meta:
        model = Addresstype
        fields = ['address_type']

class PhoneTypeTypeSerializer(serializers.ModelSerializer):
    class Meta:
        model = Phonetype
        fields = ['phone_type_id', 'phone_type']

class PhoneTypeTypeSerializerPost(serializers.ModelSerializer):
    class Meta:
        model = Phonetype
        fields = ['phone_type']

class UserAddressTypeSerializer(serializers.ModelSerializer):
    web_user = WebUserSerializer(read_only=True)
    address_type = AddressTypeSerializer(read_only=True)

    class Meta:
        model = Useraddress
        fields = [
            'user_address_id',
            'street_1',
            'street_2',
            'city',
            'st',
            'zip',
            'country',
            'created_date',
            'web_user',
            'address_type'
        ]

class UserAddressTypeSerializerPost(serializers.ModelSerializer):
    class Meta:
        model = Useraddress
        fields = ['web_user_id', 'street_1', 'street_2', 'city', 'st', 'zip', 'country', 'created_date', 'address_type']

class UserInfoTypeSerializer(serializers.ModelSerializer):
    profile_bio = serializers.SerializerMethodField()
    profile_picture = serializers.SerializerMethodField()

    class Meta:
        model = Userinfo
        fields = ['user_info_id', 'web_user', 'phone', 'date_of_birth', 'created_date', 'profile_bio', 'profile_picture']

    def get_profile_bio(self, obj):
        return "This is a placeholder biography. Welcome to my profile!"

    def get_profile_picture(self, obj):
        return "https://www.w3schools.com/howto/img_avatar.png"

class UserInfoTypeSerializerPost(serializers.ModelSerializer):
    class Meta:
        model = Userinfo
        fields = ['web_user_id', 'phone', 'date_of_birth', 'created_date']

class UserPhoneTypeSerializer(serializers.ModelSerializer):
    phone_type = PhoneTypeTypeSerializer(read_only=True)
    phone_number = serializers.CharField(source='phone')
    is_active = serializers.SerializerMethodField()

    class Meta:
        model = Userphone
        fields = ['phone_number', 'phone_type', 'is_active', 'created_date']

    def get_is_active(self, obj):
        return True

class UserPhoneTypeSerializerPost(serializers.ModelSerializer):
    class Meta:
        model = Userphone
        fields = ['web_user_id', 'phone', 'phone_type', 'created_date']

class PageDataTypeSerializer(serializers.ModelSerializer):
    class Meta:
        model = Pagedata
        fields = ['page_data_id', 'page_name', 'page_title', 'page_description', 'page_picture', 'page_menu']

class PageDataTypeSerializerPost(serializers.ModelSerializer):
    class Meta:
        model = Pagedata
        fields = ['page_name', 'page_title', 'page_description', 'page_picture', 'page_menu']
