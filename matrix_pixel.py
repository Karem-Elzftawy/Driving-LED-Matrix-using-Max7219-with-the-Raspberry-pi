#!/usr/bin/env python
# -*- coding: utf-8 -*-
# Copyright (c) 2017-18 Richard Hull and contributors
# See LICENSE.rst for details.

import time

from luma.led_matrix.device import max7219
from luma.core.interface.serial import spi, noop
from luma.core.render import canvas

def demo():
    # create matrix device
    serial = spi(port=0, device=0, gpio=noop())
    device = max7219(serial, cascaded=1, block_orientation=0, rotate=0)

    for i in  range(0, 8):
        for j in  range(0, 8):
            with canvas(device) as draw:
                draw.point((i,j), fill="red")
            time.sleep(1)

if __name__ == "__main__":

    while True:
        demo()
   
