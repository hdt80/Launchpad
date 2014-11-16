package Launchpad;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Transmitter;

import java.util.List;

public class Launchpad {

	public static void main(String[] args) {
		new Launchpad();
	}

	public Launchpad() {
		/* The device we're connection to,
		*  in this case it's our LaunchPad */
		MidiDevice device;

		/* MidiDevice.Info is all the information about a
		 * MidiDevice, like it's name, a description and more */
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();

		if (infos.length == 0) {
			System.out.println("No MIDI devices found");
		} else {
			for (MidiDevice.Info dev : infos) {
				System.out.println(dev);
			}
		}
		out("------------");

		/* Walking through each MidiDevice installed and that
		*  can currently be connected to */
		for (MidiDevice.Info info : infos) { // Fancy for each loop here
			try {
				device = MidiSystem.getMidiDevice(info); // Current device we're trying to connect to
				out(info + ""); // Printing the info of the current devide
				out("\tMidiDevice found: " + device.getDeviceInfo().getName());
				out("\t" + device.getDeviceInfo().getName() + "\'s desc: " + device.getDeviceInfo().getDescription());

				/* A Transmitter is a connection from the MidiDevice and all
				*  the data that device can send */
				List<Transmitter> transmitters = device.getTransmitters(); // Everthing the device can transmit to

				/* Setting all the MidiDevice's transmitters to our own */
				for (Transmitter transmitter : transmitters) {
					//transmitter.setReceiver(new LaunchpadReceiver(device.getDeviceInfo().toString()));
					transmitter.setReceiver(new LaunchpadReceiver(info));
				}

				Transmitter trans = device.getTransmitter();
				/* A reciever is a connection from which the MidiDevice can recieve info */
				//trans.setReceiver(new LaunchpadReceiver(device.getDeviceInfo().toString()));
				trans.setReceiver(new LaunchpadReceiver((info)));

				device.open(); // Opens a connection to the MidiDevice
				MidiSystem.getReceiver().send(new ShortMessage(176, 1, 0, 127), -1);
				out("---------------------------------\n");
				out("Connection to: \'" + device.getDeviceInfo() + "\' was made");
				out("---------------------------------\n");
			} catch (Exception e) {
				out("Missing Midi IN/OUT for " + info.getName());
				e.printStackTrace(); // Spams console and stuff still works even with it
			}
		}
		out("------------------------\n");
	}

	public void out(String msg) {
		System.out.println(msg);
	}
}
