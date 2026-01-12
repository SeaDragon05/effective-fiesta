package objects;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.usb.*;

public class NXT {
	private UsbPipe usbPipe;
	private Sensor[] sensorList = new Sensor[4];
	private Motor[] motorList = new Motor[3];
	
	public NXT() {
		try {
			UsbServices usbServices = UsbHostManager.getUsbServices();
			UsbHub rootHub = usbServices.getRootUsbHub();
			UsbDevice usbDevice = findNXT(rootHub);
			
			//UsbInterface usbInterface = usbDevice.getActiveUsbConfiguration().getUsbInterface((byte) 0);
			//UsbEndpoint endPoint = usbInterface.getUsbEndpoint((byte) 0x81);
			
		} catch (Exception e) {
			
		}
	}
	
	public UsbDevice findNXT(UsbHub hub) {
		try {
			List<String> list = hub.getAttachedUsbDevices();
			for (String item : list) {
				System.out.println(item);
			}
		} catch (UsbDisconnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
}
