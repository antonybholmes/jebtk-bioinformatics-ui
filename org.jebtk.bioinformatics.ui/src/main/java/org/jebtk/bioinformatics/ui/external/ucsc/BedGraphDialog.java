/**
 * Copyright (C) 2016, Antony Holmes
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. Neither the name of copyright holder nor the names of its contributors 
 *     may be used to endorse or promote products derived from this software 
 *     without specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.jebtk.bioinformatics.ui.external.ucsc;

import java.awt.Dimension;

import org.jebtk.bioinformatics.ext.ucsc.UCSCTrack;
import org.jebtk.modern.UI;
import org.jebtk.modern.dialog.ModernDialogTaskType;
import org.jebtk.modern.dialog.ModernDialogTaskWindow;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.window.ModernWindow;

/**
 * Allows a matrix group to be edited.
 *
 * @author Antony Holmes
 */
public class BedGraphDialog extends ModernDialogTaskWindow implements ModernClickListener {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member bed graph panel.
   */
  private TrackPanel mBedGraphPanel;

  /**
   * Instantiates a new bed graph dialog.
   *
   * @param parent the parent
   * @param bed    the bed
   */
  public BedGraphDialog(ModernWindow parent, UCSCTrack bed) {
    super(parent, ModernDialogTaskType.OK);

    mBedGraphPanel = new TrackPanel(parent, bed);

    setTitle("BedGraph Editor", bed.getName());

    setup();

    createUi();
  }

  /**
   * Setup.
   */
  private void setup() {

    mOkButton.addClickListener(this);

    setSize(new Dimension(500, 240));

    UI.centerWindowToScreen(this);
  }

  /**
   * Creates the ui.
   */
  private final void createUi() {
    // this.getWindowContentPanel().add(new JLabel("Change " +
    // getProductDetails().getProductName() + " settings", JLabel.LEFT),
    // BorderLayout.PAGE_START);

    setContent(mBedGraphPanel);
  }

  /**
   * Gets the bed graph.
   *
   * @return the bed graph
   */
  public UCSCTrack getBedGraph() {
    return mBedGraphPanel.getTrack(); // new MatrixGroup(nameField.getText(),
                                      // regexes,
                                      // colorButton.getSelectedColor());
  }
}
