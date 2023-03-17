from gpiozero import MCP3008, RGBLED, Buzzer
from time import sleep
import threading, urllib.request

led = RGBLED(red=2, green=4, blue=3)
buzzer = Buzzer(17)

adc_temp = MCP3008(channel=0)
adc_pres = MCP3008(channel=1)

temperature = 0
pressure = 0

def convert_temp(gen):
    for value in gen:
        #print(value)
        yield (value * 3.3 - 0.73) * 45.2 - 33

def convert_pres(gen_pres):
    for value_pres in gen_pres:
        #print(value_pres)
        if value_pres <= 0.0005:
            yield 0
        #elif value_pres <= 0.7:
        #yield (3.3*value_pres+0.89)/0.032
        else:
            yield value_pres
            #yield (3.3*value_pres-2.14)*600

def print_temp():
    for temp in convert_temp(adc_temp.values):
        print('The temperature is', round(temp,1), '°C.')
        temperature = round(temp,1)
        if temp > 30:
            led.color = (1, 0, 0)
        elif 22 < temp < 30:
            led.color = (0,1 ,0)
        else:
            led.color = (0, 0, 1)
        sleep(1)

def print_pres():
    for pres in convert_pres(adc_pres.values):
        print('The pressure value is', pres)
        pressure = pres
        if pres <= 0.3:
            buzzer.off()
        else:
            buzzer.on()
        sleep (1)

thread1 = threading.Thread(target=print_temp)
thread1.start()

thread2 = threading.Thread(target=print_pres)
thread2.start()

webUrl = urllib.request.urlopen('https://studev.groept.be/api/a22ib2c01/InsertMeasurementValue/' + str(temperature) + "/" + str(pressure))



