package Launchpad;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class LaunchpadReceiver implements Receiver {
	public MidiDevice.Info info;
	public Robot r; // Robot for moving the mouse and pressing keys

	public LaunchpadReceiver (MidiDevice.Info info) {
		this.info = info;
		try {
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fired when a Midi button is pressed
	 *
	 * @param msg       the message the Midi device sent
	 * @param timeStamp the time when the Midi device sent the MidiMessage
	 *                  we never used this because we don't do anything
	 *                  that needs timings
	 */
	public void send (MidiMessage msg, long timeStamp) {
		ShortMessage sm = (ShortMessage) msg;
		System.out.printf("A Midi button on %s was %s: %d%n", info.getName(), ((sm.getData2() == 0) ? "release" : "pressed"), sm.getData1());
		if (sm.getData1() == 6 && sm.getData2() == 0) {
			moveMouse(0, -10);
		} else if (sm.getData1() == 21 && sm.getData2() == 0) {
			moveMouse(-10, 0);
		} else if (sm.getData1() == 23 && sm.getData2() == 0) {
			moveMouse(10, 0);
		} else if (sm.getData1() == 38 && sm.getData2() == 0) {
			moveMouse(0, 10);
		} else if (sm.getData1() == 5 && sm.getData2() == 0) {
			pressKey(KeyEvent.VK_PAGE_UP);
		} else if (sm.getData1() == 7 && sm.getData2() == 0) {
			pressKey(KeyEvent.VK_PAGE_DOWN);
		} else if (sm.getData1() == 0 && sm.getData2() == 0) {
			execute("D:\\RiotGames\\lol.launcher.exe");
		} else if (sm.getData1() == 16 && sm.getData2() == 0) {
			execute("taskkill -im skype.exe -f");
		}
	}

	/**
	 * Closes an existing connection to a Midi device
	 * Unused, as we never close the connection while running
	 */
	public void close () {

	}

	/**
	 * Presses a keyboard key
	 *
	 * @param keyCode the key to press
	 */
	private void pressKey (int keyCode) {
		r.keyPress(keyCode);
		r.keyRelease(keyCode);
	}

	/**
	 * Presses a mouse button
	 * 0 - Left
	 * 1 - Middle
	 * 2 - Right
	 *
	 * @param button
	 */
	private void clickMouse (int button) {
		r.mousePress(button);
		r.mouseRelease(button);
	}

	/**
	 * Moves your mouse a number of pixels
	 *
	 * @param x the amount of pixels to move to left-right (Negative is left)
	 * @param y the amount of pixels to move to up-down (Negative is up)
	 */
	private void moveMouse (int x, int y) {
		Point mousePoint = MouseInfo.getPointerInfo().getLocation();
		r.mouseMove(mousePoint.x + x, mousePoint.y + y);
	}

	/**
	 * Scroll your mouse wheel a certain amount
	 *
	 * @param amount Amount of "ticks" to scroll (Negative is down)
	 */
	private void scrollMouse (int amount) {
		r.mouseWheel(amount);
	}

	/**
	 * Execute a program from the given path
	 *
	 * @param path the path of the program being ran
	 */
	private void execute (String path) {
		System.out.printf("Executing programing in \'%s\'%n", path);
		try {
			Runtime.getRuntime().exec(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Program executed");
	}
}
