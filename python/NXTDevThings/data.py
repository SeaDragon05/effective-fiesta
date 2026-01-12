#!/usr/bin/python3
"""NXT-Python tutorial: find the brick."""
import nxt.locator
from time import sleep
import logging

#logging.basicConfig(level=logging.DEBUG)
# Find a brick.
with nxt.locator.find() as b:
    # Once found, print its name.
    print("Found brick:", b.get_device_info()[0])
    # And play a recognizable note.
    
# List of piano key pitches in hertz

ds5 	= 622
f5 	= 698
fs5 	= 740
gs5	= 830
as5	= 932
ds6	= 1245
cs6	= 1108
d5	= 587
cs5	= 554
f6	= 1397
fs6	= 1480

melody = [
    (ds5, 0.2),
    (f5, 0.2),
    (fs5, 0.2),
    (gs5, 0.2),
    (as5, 0.4),
    (ds6, 0.2),
    (cs6, 0.2),
    (as5, 0.4),
    (ds5, 0.4),
    (as5, 0.2),
    (gs5, 0.2),
    (fs5, 0.2),
    (f5, 0.2),
    (ds5, 0.2),
    (f5, 0.2),
    (fs5, 0.2),
    (gs5, 0.2),
    (as5, 0.4),
    (gs5, 0.2),
    (fs5, 0.2),
    (f5, 0.2),
    (ds5, 0.2),
    (f5, 0.2),
    (fs5, 0.2),
    (f5, 0.2),
    (ds5, 0.2),
    (d5, 0.2),
    (f5, 0.2),
    (ds5, 0.2),
    (f5, 0.2),
    (fs5, 0.2),
    (gs5, 0.2),
    (as5, 0.4),
    (ds6, 0.2),
    (cs6, 0.2),
    (as5, 0.4),
    (ds5, 0.4),
    (as5, 0.2),
    (gs5, 0.2),
    (fs5, 0.2),
    (f5, 0.2),
    (ds5, 0.2),
    (f5, 0.2),
    (fs5, 0.2),
    (gs5, 0.2),
    (as5, 0.4),
    (gs5, 0.2),
    (fs5, 0.2),
    (f5, 0.4),
    (fs5, 0.4),
    (gs5, 0.4),
    (as5, 0.4),
]

section2 = [#repeated 3 times

    (gs5, 0.2),
    (as5, 0.2),
    (cs6, 0.2),
    (ds6, 0.2),
    (as5, 0.2),
    (gs5, 0.2),
    (as5, 0.4),

    (gs5, 0.2),
    (as5, 0.2),
    (cs6, 0.2),
    (ds6, 0.2),
    (as5, 0.2),
    (gs5, 0.2),
    (as5, 0.4),
    
    
    (gs5, 0.2),
    (as5, 0.2),
    (gs5, 0.2),
    (fs5, 0.2),
    (f5, 0.2),
    (cs5, 0.2),
    (ds5, 0.4),
    
    (cs5, 0.2),
    (ds5, 0.2),
    (f5, 0.2),
    (fs5, 0.2),
    (gs5, 0.2),
    (as5, 0.2),
    (ds5, 0.4),
]

section3 = [

    (gs5, 0.2),
    (as5, 0.2),
    (cs6, 0.2),
    (ds6, 0.2),
    (as5, 0.2),
    (gs5, 0.2),
    (as5, 0.4),
    (gs5, 0.2),
    (as5, 0.2),
    (cs6, 0.2),
    (ds6, 0.2),
    (as5, 0.2),
    (gs5, 0.2),
    (as5, 0.4),
    (ds6, 0.2),
    (f6, 0.2),
    (fs6, 0.2),
    (f6, 0.2),
    (ds6, 0.2),
    (cs6, 0.2),
    (as5, 0.4),
    (gs5, 0.2),
    (as5, 0.2),
    (gs5, 0.2),
    (fs5, 0.2),
    (f5, 0.2),
    (cs5, 0.2),
    (ds5, 0.4),
]

def playKey(key, time, brick):
    response = brick.play_tone(key, int((time * 1000)))
    print("Playing note: " + str(key) + " for " + str(time * 1000) + " ms")
    sleep(time)
    

def playBadApple():
    # Replace the 'brick' argument with the instance of your EV3 brick
    brick = nxt.locator.find()
    for note in melody:
        key, time = note
        playKey(key, time, brick)
    for note in melody:
        key, time = note
        playKey(key, time, brick)
    for note in section2:
        key, time = note
        playKey(key, time, brick)
    for note in section2:
        key, time = note
        playKey(key, time, brick)
    for note in section2:
        key, time = note
        playKey(key, time, brick)
    
    for note in section3:
        key, time = note
        playKey(key, time, brick)
    
    for note in section2:
        key, time = note
        playKey(key, time, brick)
    for note in section2:
        key, time = note
        playKey(key, time, brick)
    
    for note in section3:
        key, time = note
        playKey(key, time, brick)

if __name__ == "__main__":
    playBadApple()
    print("Complete")


    
    
