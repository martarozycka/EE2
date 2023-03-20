from time import sleep

from gpiozero import MCP3008, RGBLED, Buzzer
import requests

led = RGBLED(red=2, green=4, blue=3)
buzzer = Buzzer(17)

while True:
    temperature = ((MCP3008(channel=0).value * 3.3 - 0.73) * 45.2 - 33)
    print(temperature)
    pressure = MCP3008(channel=1).value
    if pressure < 0.0005:
        pressure = 0
    print(pressure)
    if pressure <= 0.4:
        buzzer.off()
    else:
        buzzer.on()
    if temperature > 30:
        led.color = (1, 0, 0)
    elif 22 < temperature < 30:
        led.color = (0,1 ,0)
    else:
        led.color = (0, 0, 1)
    sleep(1)
    requests.get("https://studev.groept.be/api/a22ib2c01/InsertMeasurementValue/"+str(temperature)+"/" + str(pressure))






