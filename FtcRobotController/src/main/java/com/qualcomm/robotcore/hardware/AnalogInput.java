/*
 * Copyright (c) 2014, 2015 Qualcomm Technologies Inc
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * (subject to the limitations in the disclaimer below) provided that the following conditions are
 * met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 * and the following disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of Qualcomm Technologies Inc nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS LICENSE. THIS
 * SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.qualcomm.robotcore.hardware;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.hardware.configuration.annotations.AnalogSensorType;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

/**
 * Control a single analog device
 */
@AnalogSensorType
@DeviceProperties(name = "@string/configTypeAnalogInput", xmlTag = "AnalogInput", builtIn = true)
public class AnalogInput implements HardwareDevice {

  private AnalogInputController controller = null;
  private int channel = -1;

  /**
   * Constructor
   *
   * @param controller AnalogInput controller this channel is attached to
   * @param channel channel on the analog input controller
   */
  public AnalogInput(AnalogInputController controller, int channel) {
    this.controller = controller;
    this.channel = channel;
  }

  @Override public Manufacturer getManufacturer() {
    return controller.getManufacturer();
  }

  /**
   * Returns the current voltage of this input.
   * @return the current analog input voltage, in volts.
   */
  public double getVoltage() {
    return controller.getAnalogInputVoltage(channel);
  }

  /**
   * Returns the maximum value that getVoltage() is capable of reading
   * @return the maximum value that getVoltage() is capable of reading, in volts.
   * @see #getVoltage()
   */
  public double getMaxVoltage() {
    return controller.getMaxAnalogInputVoltage();
  }

  @Override
  public String getDeviceName() {
    return AppUtil.getDefContext().getString(R.string.configTypeAnalogInput);
  }

  @Override
  public String getConnectionInfo() {
    return controller.getConnectionInfo() + "; analog port " + channel;
  }

  @Override
  public int getVersion() {
    return 1;
  }

  @Override
  public void resetDeviceConfigurationForOpMode() {
  }

  @Override
  public void close() {
    // take no action
  }
}