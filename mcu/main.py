import network
import time
import utime
import urequests
import machine
from hcsr04 import HCSR04


def do_connect():

    sta_if = network.WLAN(network.STA_IF)
    if not sta_if.isconnected():
        print('connecting to network...')
        sta_if.active(True)
        sta_if.connect('Koodo LTE', 'papiday1234')
        while not sta_if.isconnected():
            pass
    print('network config:', sta_if.ifconfig())

def makeApicall(occupied):
    if occupied:
        response = urequests.request ("PUT",
                                      "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/spot?spotId=5c7975977d2dff31f89e0d87",
                                      '{"occupied":"true"}', None, {"Content-Type": "application/json"})
        response.close()
    else:

        response = urequests.request ("PUT",
                                      "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/spot?spotId=5c7975977d2dff31f89e0d87",
                                      '{"occupied":"false"}', None, {"Content-Type": "application/json"})
        response.close ()


def main():
    do_connect ()
    led = machine.Pin (2, machine.Pin.OUT)
    sensor = HCSR04 (trigger_pin=5, echo_pin=4)
    #while True:
    for i in range(200):
        distance = sensor.distance_cm ()
        if(distance > 1 and distance < 390):
            led.off ()  # led.off() actually means on, must be active low
            makeApicall(True)
        else:
            led.on ()
            makeApicall(False)
        print ('Distance:', distance, 'cm')
        time.sleep(1.0)


if __name__ == '__main__':
      main ()















