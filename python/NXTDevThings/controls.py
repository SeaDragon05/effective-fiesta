import nxt.locator
import nxt.sensor
from time import sleep
import keyboard

ma = None
mb = None
mc = None


def arrow_key_function(e):
    if e.event_type == keyboard.KEY_DOWN:
        if e.name == 'up':
            print("Up arrow key pressed")
            # Call your specific function here
        elif e.name == 'down':
            print("Down arrow key pressed")
            # Call your specific function here
        elif e.name == 'left':
            print("Left arrow key pressed")
            # Call your specific function here
        elif e.name == 'right':
            print("Right arrow key pressed")


keyboard.on_press_key('up', arrow_key_function)
keyboard.on_press_key('down', arrow_key_function)
keyboard.on_press_key('left', arrow_key_function)
keyboard.on_press_key('right', arrow_key_function)

keyboard.wait('esc')