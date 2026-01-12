package main;

import java.nio.ByteBuffer;
import java.util.List;

import javax.usb.*;

import objects.NXT;

public class Main {
	//important stuff first (idk what to call it)
	
	static final byte nullTerminator = 0x00;//NXT's null thing... ya know... right?
	
	static final int MAX_BYTES_IN_MESSAGE = 64;//DO NOT MAKE THIS BIGGER THAN 64!!
	
	
	//byte 0 protocalls from the manual:
	
	// DIRECTC REPLY REQUIRED
	// Direct command to the NXT where a reply is required
	// This instruction is used for direct communication with the NXT
	// Commands such as data streams, sensors, and motor commands
	static final byte DIRECTC_REPLY_REQUIRED 				= (byte) 0x00;
	// SYSTEMC REPLY REQUIRED
	// System command to the NXT where a reply is required
	// This instruction is used for system calls with the NXT
	// Commands such as reading/writing files, getting file data, etc.
	static final byte SYSTEMC_REPLY_REQUIRED 				= (byte) 0x01;
	// REPLY COMMAND
	// return value used by the NXT when recieveing information
	// Any data following this is data called from the NXT
	static final byte REPLY_COMMAND 						= (byte) 0x02;
	
	static final byte DIRECTC_SET_OUTPUT_STATE_MOTOR 		= (byte) 0x04;
	static final byte DIRECTC_SET_OUTPUT_STATE_SENSOR 		= (byte) 0x07;
	
	static final byte DIRECTC_REPLY_NOT_REQUIRED 			= (byte) 0x80;
	static final byte SYSTEMC_REPLY_NOT_REQUIRED 			= (byte) 0x81;
	
	//chatGPT generated list of commands (dont use yet):
	// Command to start a direct command program
	static final byte START_PROGRAM 						= (byte) 0x00;
	// Command to stop the currently running program
	static final byte STOP_PROGRAM 							= (byte) 0x01;
	// Command to play a sound file
	static final byte PLAY_SOUND_FILE 						= (byte) 0x02;
	// Command to play a tone
	static final byte PLAY_TONE 							= (byte) 0x03;
	// Command to set the output state of a motor
	static final byte SET_OUTPUT_STATE 						= (byte) 0x04;
	// Command to set input mode of a sensor
	static final byte SET_INPUT_MODE 						= (byte) 0x05;
	// Command to get the output state of a motor
	static final byte GET_OUTPUT_STATE 						= (byte) 0x06;
	// Command to get the input values of a sensor
	static final byte GET_INPUT_VALUES 						= (byte) 0x07;
	// Command to reset the motor position
	static final byte RESET_MOTOR_POSITION 					= (byte) 0x0A;
	// Command to stop all motors
	static final byte STOP_ALL_MOTORS 						= (byte) 0x0E;
	// Command to keep the NXT alive
	static final byte KEEP_ALIVE 							= (byte) 0x0D;
	// Command to get the firmware version
	static final byte GET_FIRMWARE_VERSION 					= (byte) 0x88;
	// Command to get device information
	static final byte GET_DEVICE_INFO 						= (byte) 0x9B;
	// Command to open a file on the NXT brick
	static final byte OPEN_FILE 							= (byte) 0x82;
	// Command to read data from a file
	static final byte READ_FILE 							= (byte) 0x83;
	// Command to write data to a file
	static final byte WRITE_FILE 							= (byte) 0x84;
	// Command to close a file on the NXT brick
	static final byte CLOSE_FILE 							= (byte) 0x85;
	// Command to delete a file on the NXT brick
	static final byte DELETE_FILE 							= (byte) 0x86;
	// Command to find a specific file on the NXT brick
	static final byte FIND_FILE 							= (byte) 0x8B;
	// Command to get the NXT battery level
	static final byte GET_BATTERY_LEVEL 					= (byte) 0x0B;
	// Command to stop the NXT firmware
	static final byte STOP_NXT 								= (byte) 0x00;
	// Command to start the NXT firmware
	static final byte START_NXT 							= (byte) 0x01;
	// Command to play a sound file (looped)
	static final byte PLAY_SOUND_FILE_LOOP 					= (byte) 0x03;
	// Command to get the button state
	static final byte GET_BUTTON_STATE 						= (byte) 0x07;
	// Command to get the firmware version (batched)
	static final byte GET_FIRMWARE_VERSION_BATCHED 			= (byte) 0x98;
	// Command to get the battery level (batched)
	static final byte GET_BATTERY_LEVEL_BATCHED 			= (byte) 0x0C;
	// Command to set the NXT name
	static final byte SET_BRICK_NAME 						= (byte) 0x98;
	// Command to set the NXT Bluetooth address
	static final byte SET_BT_ADDRESS 						= (byte) 0x9A;
	// Command to get the NXT Bluetooth address
	static final byte GET_BT_ADDRESS						= (byte) 0x9D;
	// Command to set the NXT communication protocol
	static final byte SET_COMMUNICATION 					= (byte) 0x9B;
	// Command to get the NXT communication protocol
	static final byte GET_COMMUNICATION 					= (byte) 0x9C;
	// Command to set the NXT brick user interface
	static final byte SET_UI_DRAW 							= (byte) 0x84;
	// Command to refresh the NXT brick display
	static final byte UPDATE_UI 							= (byte) 0x85;
	// Command to read a message from the NXT brick mailbox
	static final byte MESSAGE_READ 							= (byte) 0x13;
	// Command to write a message to the NXT brick mailbox
	static final byte MESSAGE_WRITE 						= (byte) 0x09;
	// Command to reset the NXT brick (soft reset)
	static final byte RESET_BRICK 							= (byte) 0x08;
	// Command to erase the NXT flash memory
	static final byte FLASH_ERASE 							= (byte) 0xA0;
	// Command to send an I2C command
	static final byte I2C_COMMUNICATION 					= (byte) 0x81;
	// Command to update the NXT firmware (boot mode)
	static final byte UPDATE_FIRMWARE 						= (byte) 0x19;
	
	
	//commands for things (?)
	
	//ports
	static final byte PORT_1 								= (byte) 0x01;
	static final byte PORT_2 								= (byte) 0x02;
	static final byte PORT_3 								= (byte) 0x03;
	static final byte PORT_4 								= (byte) 0x04;
	
	//data return types for sensors:
	
	static final byte SENSOR_DATA_READ_ALL_AVAILABLE 		= (byte) 0x00;
	
	//The NXT will return a value when soething finishes.
	//0 is what is expected, but anything above that is an issue
	//These are the return values:
	static final byte ERROR_MSG_SUCCESS  					= (byte) 0x00;
	static final byte ERROR_MSG_PENDING_COMM_TA_IN_PROGRESS = (byte) 0x20;
	static final byte ERROR_MSG_SPECIFED_MAILBOX_QUEUE_EMPTY= (byte) 0x40;
	static final byte ERROR_MSG_NO_MORE_HANDLES  			= (byte) 0x81;
	static final byte ERROR_MSG_NO_SPACE  					= (byte) 0x82;
	static final byte ERROR_MSG_NO_MORE_FILES				= (byte) 0x83;
	static final byte ERROR_MSG_END_OF_FILE_EXPECTED  		= (byte) 0x84;
	static final byte ERROR_MSG_END_OF_FILE  				= (byte) 0x85;
	static final byte ERROR_MSG_NOT_A_LINEAR_FILE 			= (byte) 0x86;
	static final byte ERROR_MSG_FILE_NOT_FOUND 				= (byte) 0x87;
	static final byte ERROR_MSG_HANDLE_ALL_READY_CLOSED 	= (byte) 0x88;
	static final byte ERROR_MSG_NO_LINEAR_SPACE 			= (byte) 0x89;
	static final byte ERROR_MSG_UNDEFINED_ERROR 			= (byte) 0x8A;
	static final byte ERROR_MSG_FILE_IS_BUSY 				= (byte) 0x8B;
	static final byte ERROR_MSG_NO_WRITE_BUFFERS 			= (byte) 0x8C;
	static final byte ERROR_MSG_APPEND_NOT_POSSIBLE 		= (byte) 0x8D;
	static final byte ERROR_MSG_FILE_IS_FULL 				= (byte) 0x8E;
	static final byte ERROR_MSG_FILE_EXISTS 				= (byte) 0x8F;
	static final byte ERROR_MSG_MODULE_NOT_FOUND			= (byte) 0x90;
	static final byte ERROR_MSG_OUT_OF_BOUNDARY				= (byte) 0x91;
	static final byte ERROR_MSG_ILLEGAL_FILE_NAME			= (byte) 0x92;
	static final byte ERROR_MSG_ILLEGAL_HANDLE				= (byte) 0x93;
	static final byte ERROR_MSG_REQUEST_FAILED				= (byte) 0xBD;
	static final byte ERROR_MSG_UNKNOWN_COMMAND_OPCODE		= (byte) 0xBE;
	static final byte ERROR_MSG_INSANE_PACKET				= (byte) 0xBF;
	static final byte ERROR_MSG_DATA_OUT_OF_RANGE			= (byte) 0xC0;
	static final byte ERROR_MSG_COMM_BUS_ERROR				= (byte) 0xDD;
	static final byte ERROR_MSG_NO_FREE_MEM_IN_COMM_BUFFER	= (byte) 0xDE;
	static final byte ERROR_MSG_SPECIFIED_CONNECTION_INVALID= (byte) 0xDF;
	static final byte ERROR_MSG_SPECIFIED_CONNECTION_NOT_CONFIGURED_OR_BUSY
															= (byte) 0xE0;
	static final byte ERROR_MSG_NO_ACTIVE_PROGRAM			= (byte) 0xEC;
	static final byte ERROR_MSG_ILLEGAL_SIZE				= (byte) 0xED;
	static final byte ERROR_MSG_ILLEGAL_MAILBOX_Q_ID_SPEC	= (byte) 0xEE;
	static final byte ERROR_MSG_INVALID_FIELD				= (byte) 0xEF;
	static final byte ERROR_MSG_BAD_INPUT_OUTPUT_SPEC		= (byte) 0xF0;
	static final byte ERROR_MSG_OUT_OF_MEM					= (byte) 0xFB;
	static final byte ERROR_MSG_BAD_ARGUMENTS				= (byte) 0xFF;
	
	
	
	static final byte[] ERROR_MSG_ARRAY = {
			ERROR_MSG_SUCCESS, ERROR_MSG_PENDING_COMM_TA_IN_PROGRESS,
			ERROR_MSG_SPECIFED_MAILBOX_QUEUE_EMPTY, ERROR_MSG_NO_MORE_HANDLES,
			ERROR_MSG_NO_SPACE, ERROR_MSG_NO_MORE_FILES, ERROR_MSG_END_OF_FILE_EXPECTED,
			ERROR_MSG_END_OF_FILE, ERROR_MSG_NOT_A_LINEAR_FILE, ERROR_MSG_FILE_NOT_FOUND,
			ERROR_MSG_HANDLE_ALL_READY_CLOSED, ERROR_MSG_NO_LINEAR_SPACE, 
			ERROR_MSG_UNDEFINED_ERROR, ERROR_MSG_FILE_IS_BUSY, ERROR_MSG_NO_WRITE_BUFFERS,
			ERROR_MSG_APPEND_NOT_POSSIBLE, ERROR_MSG_FILE_IS_FULL, ERROR_MSG_FILE_EXISTS,
			ERROR_MSG_MODULE_NOT_FOUND, ERROR_MSG_OUT_OF_BOUNDARY, ERROR_MSG_ILLEGAL_FILE_NAME,
			ERROR_MSG_ILLEGAL_HANDLE, ERROR_MSG_REQUEST_FAILED, ERROR_MSG_UNKNOWN_COMMAND_OPCODE,
			ERROR_MSG_INSANE_PACKET, ERROR_MSG_DATA_OUT_OF_RANGE, ERROR_MSG_COMM_BUS_ERROR,
			ERROR_MSG_NO_FREE_MEM_IN_COMM_BUFFER, ERROR_MSG_SPECIFIED_CONNECTION_INVALID,
			ERROR_MSG_SPECIFIED_CONNECTION_NOT_CONFIGURED_OR_BUSY, ERROR_MSG_NO_ACTIVE_PROGRAM,
			ERROR_MSG_ILLEGAL_SIZE, ERROR_MSG_ILLEGAL_MAILBOX_Q_ID_SPEC, ERROR_MSG_INVALID_FIELD,
			ERROR_MSG_BAD_INPUT_OUTPUT_SPEC, ERROR_MSG_OUT_OF_MEM, ERROR_MSG_BAD_ARGUMENTS};
	static final String[] ERROR_MSG_STRING_ARRAY = {"SUCCESS", 
			"PENDING_COMMUNICATION_TRANSACTION_IN_PROGRESS",
			"SPECIFIED_MAILBOX_QUEUE_IS_EMPTY", "NO_MORE_HANDLES",
			"NO_SPACE", "NO_MORE_FILES", "END_OF_FILE_EXPECTED",
			"END_OF_FILE", "NOT_A_LINEAR_FILE", "FILE_NOT_FOUND",
			"HANDLE_ALL_READY_CLOSED", "NO_LINEAR_SPACE", 
			"UNDEFINED ERROR", "FILE_IS_BUSY", "NO_WRITE_BUFFERS",
			"APPEND_NOT_POSSIBLE", "FILE_IS_FULL", "FILE_EXISTS",
			"MODULE_NOT_FOUND", "OUT_OF_BOUNDARY", "ILLEGAL_FILE_NAME",
			"ILLEGAL_HANDLE", "REQUEST_FAILED", "UNKNOWN_COMMAND_OPCODE",
			"INSANE_PACKET", "DATA_CONTAINS_OUT-OF-RANGE_VALUES",
			"COMMUNICATION_BUS_ERROR", "NO_FREE_MEMORY_IN_COMMUNICATION_BUFFER",
			"SPECIFIED_CHANNEL_OR_CONNECTION_IS_NOT_VALID",
			"SPECIFIED_CHANNEL_OR_CONNECTION_NOT_CONFIGURED_OR_BUSY",
			"NO_ACTIVE_PROGRAM", "ILLEGAL_SIZE_SPECIFIED", 
			"ILLEGAL_MAILBOX_QUEUE_ID_SPECIFIED",
			"ATTEMPTED_TO_ACCESS_INVALID_FIELD_OF_A_STRUCTUE",
			"BAD_INPUT-OUT_OUTPUT_SPECIFIED", "INSUFFICENT_MEMORY_AVAILABLE",
			"BAD_ARGUMENTS"};

	public static void main(String[] args) throws SecurityException, UsbException {
		//create connection with the brick
		//ask it for the firmware version
		//????
		//profit
		//testMessage();
		UsbDevice NXT = findNXT();
		UsbInterface usbInterface = NXT.getActiveUsbConfiguration().getUsbInterface((byte) 0);
        UsbEndpoint endpoint = usbInterface.getUsbEndpoint((byte) 0x81); // Change the endpoint address accordingly
        UsbPipe usbPipe = endpoint.getUsbPipe();
        usbPipe.open();
        //int bytesRead = usbPipe.syncSubmit(playTone(500, 1000));
        //System.out.println(bytesRead);
        usbPipe.close();
	}
	
	public static UsbDevice findNXT() throws SecurityException, UsbException {
		UsbServices US = UsbHostManager.getUsbServices();
		UsbHub rootHub = US.getRootUsbHub();
		UsbDevice NXT = (UsbDevice) rootHub.getAttachedUsbDevices().get(0);
		return NXT;
	}
	
	protected static void testMessage() {
		byte[] message = {SYSTEMC_REPLY_REQUIRED, GET_DEVICE_INFO};
		//send message
		byte[] rp = new byte[32];
		parseDeviceInfo(getFakeReturnMessage(message));
		
		
		
	}
	
	public static void sendDirectMessage(byte[] message) {
		//TODO: add code that sends the message in the USB pipe to the NXT
		
	}
	
	public static ByteBuffer playTone(int frequency, int timeMS) {
		byte[] m = new byte[6];
		m[0] = DIRECTC_REPLY_REQUIRED;
		m[1] = PLAY_TONE;
		byte[] tones = convertIntIntoByteArray(frequency);
		m[2] = tones[0];
		m[3] = tones[1];
		byte[] times = convertIntIntoByteArray(timeMS);
		m[4] = times[0];
		try {
			m[5] = times[1];
		} catch (Exception e) {
			m[5] = 0x00;
		}
		
		ByteBuffer message = ByteBuffer.allocate(6);
		message.put(m);
		return message;
	}

	public static void playSoundFile() {
		
	}
	
	public static void startMotor() {
		
	}
	
	/**
	 * @function convertIntIntoByteArray
	 * @param value
	 * value is the integer that is to be converted into the byte array for various NXT functions
	 * @return byteArray
	 * @author ChatGPT
	 */
	protected static byte[] convertIntIntoByteArray(int value) {
		int byteArraySize = 0;
		if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
			byteArraySize = 1;
		} else if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
			byteArraySize = 2;
		} else {
			byteArraySize = 4;
		}
		byte[] byteArray = new byte[byteArraySize];
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(value);
		buffer.position(4 - byteArraySize);
		buffer.get(byteArray);
		return byteArray;
	}
	
	protected static void parseDeviceInfo(byte[] rp) {
		if (rp[0] != 0x02) {
			System.out.println("Error: Not a return message!");
			return;
		}
		if (rp[1] != (byte) 0x9B) {
			System.out.println("Error: Return type is not device info!\nExpected " + (byte) 0x9B + " Got " + rp[1]);
			return;
		}
		System.out.println("Status: " + parseErrorMessage(rp[2]));
		System.out.println("Name of Device: ");
		for (int i = 3; i < 17; i ++) {
			System.out.print((char) rp[i]);
		}
		System.out.println("\nBlueTooth address: ");
		for (int i = 18; i < 24; i ++) {
			System.out.print(rp[i]);
		}
		System.out.println("\nLSB of Bluetooth Signal strength: " + rp[25]);
		
	}
	
	
	protected static String parseErrorMessage(byte b) {
		for (int i = 0; i < ERROR_MSG_ARRAY.length; i++) {
			if (b == ERROR_MSG_ARRAY[i]) {
				return ERROR_MSG_STRING_ARRAY[i];
			}
		}
		return "ERROR ERROR: UNDEFINED You shouldn't be seeing this";
	}

	protected static byte[] getFakeReturnMessage(byte[] message) {
		byte[] rm = new byte[33];
		rm[0] = 0x02;
		rm[1] = (byte) 0x9B;
		rm[2] = (byte) 0x00;
		rm[3] = 0x69; 
		rm[4] = 0x6b; 
		rm[5] = 0x65; 
		rm[6] = 0x35; 
		rm[7] = 0x36; 
		rm[8] = 0x37; 
		rm[9] = 0x35; 
		rm[10] = nullTerminator;
		rm[11] = 0x00;
		rm[12] = 0x00;
		rm[13] = 0x00;
		rm[14] = 0x00;
		rm[15] = 0x00;
		rm[16] = 0x00;
		rm[17] = nullTerminator;
		rm[18] = 0x01;
		rm[19] = 0x01;
		rm[20] = 0x01;
		rm[21] = 0x01;
		rm[22] = 0x01;
		rm[23] = 0x01;
		rm[24] = 0x01;
		rm[25] = 0x00;
		//rm[26] = 0x00;
		//rm[27] = 0x00;
		rm[28] = 0x00;
		rm[29] = (byte) 0x9F;
		//rm[30] = 0x00;
		//rm[31] = 0x00;
		rm[32] = 0x65;
		return rm;
	}
	
}
