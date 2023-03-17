from gpiozero import MCP3008, RGBLED, Buzzer
from time import sleep
import threading, urllib.request




led = RGBLED(red=2, green=4, blue=3)
buzzer = Buzzer(17)

while True:
    temperature = ((MCP3008(channel=0).value * 3.3 - 0.73) * 45.2 - 33)
    print(temperature)
    pressure = MCP3008(channel=1).value
    print(pressure)
    if pressure <= 0.3:
        buzzer.off()
    else:
        buzzer.on()
    if temperature > 30:
        led.color = (1, 0, 0)
    elif 22 < temperature < 30:
        led.color = (0,1 ,0)
    else:
        led.color = (0, 0, 1)

webUrl = urllib.request.urlopen('https://studev.groept.be/api/a22ib2c01/InsertMeasurementValue/' + str(temperature) + "/" + str(pressure))



