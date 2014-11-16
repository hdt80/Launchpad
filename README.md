Launchpad Controller
====================

A Java program that will execute code when you press a button on a connected [Launchpad](http://us.novationmusic.com/midi-controllers-digital-dj/launchpad-s).

The actual code is ran in LaunchpadReceiver.java in the send function.

Each button is mapped to a different value, with two different states. 0 and 127. 0 means the button is released, and 127 means the button is currently being pressed. Maybe I'll get a diagram of what each button is later.

I couldn't get sending message to my Launchpad to work, but the general idea is shown in Launchpad.java on line 59. Read more [here](https://d19ulaff0trnck.cloudfront.net/sites/default/files/novation/downloads/4080/launchpad-programmers-reference.pdf) for a better understanding of how sending works.
