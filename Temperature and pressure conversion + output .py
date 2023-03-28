from gpiozero import MCP3008, RGBLED, Buzzer
import requests

led = RGBLED(red=2, green=4, blue=3)
buzzer = Buzzer(17)

while True:
    borders = requests.get("https://studev.groept.be/api/a22ib2c01/selectMinMax")
    jsonresponse = borders.json()[0]
    minTemp = jsonresponse["min_temp"]
    maxTemp = jsonresponse["max_temp"]
    minWeight = jsonresponse["min_pres"]
    maxWeight = jsonresponse["max_pres"]
    temperature = ((MCP3008(channel=0).value * 3.3 - 0.73) * 45.2 - 33)
    print(temperature)
    pressure = MCP3008(channel=1).value * 50
    if pressure < 8: #filter out noise
        pressure = 0
    weight = 6.625*pressure
    print(weight)
    if weight <= maxWeight:
        buzzer.off()
    else:
        buzzer.on()
    if temperature > maxTemp:
        led.color = (1, 0, 0)
    elif minTemp < temperature < maxTemp:
        led.color = (0,1 ,0)
    else:
        led.color = (0, 0, 1)
    requests.get("https://studev.groept.be/api/a22ib2c01/InsertMeasurementValue/"+str(temperature)+"/" + str(weight))







