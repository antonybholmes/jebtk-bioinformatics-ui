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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Box;

import org.jebtk.bioinformatics.ext.ucsc.BedGraphGroupsModel;
import org.jebtk.modern.ModernWidget;
import org.jebtk.modern.UI;
import org.jebtk.modern.dialog.ModernDialogTaskWindow;
import org.jebtk.modern.panel.MatrixPanel;
import org.jebtk.modern.panel.ModernPanel;
import org.jebtk.modern.text.ModernAutoSizeLabel;
import org.jebtk.modern.text.ModernTextBorderPanel;
import org.jebtk.modern.text.ModernTextField;
import org.jebtk.modern.window.ModernWindow;

/**
 * Allows a matrix group to be edited.
 *
 * @author Antony Holmes
 */
public class BedGraphGroupDialog extends ModernDialogTaskWindow {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member name field.
   */
  private ModernTextField mNameField = new ModernTextField("Name");

  /**
   * The member old.
   */
  private String mOld;

  /**
   * The class KeyEvents.
   */
  private class KeyEvents extends KeyAdapter {

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyAdapter#keyReleased(java.awt.event.KeyEvent)
     */
    @Override
    public void keyReleased(KeyEvent e) {
      setTitle("BedGraph Group", mNameField.getText());
    }
  }

  /**
   * Instantiates a new bed graph group dialog.
   *
   * @param parent the parent
   * @param model the model
   * @param oldGroup the old group
   */
  public BedGraphGroupDialog(ModernWindow parent, BedGraphGroupsModel model,
      String oldGroup) {
    super(parent);

    mOld = oldGroup;

    if (mOld != null) {
      mNameField.setText(mOld);
    } else {
      mNameField.setText("Group " + (model.size() + 1));
    }

    setTitle("BedGraph Group");

    setup();

    createUi();
  }

  /**
   * Setup.
   */
  private void setup() {
    mNameField.addKeyListener(new KeyEvents());

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

    Box box = Box.createVerticalBox();

    int[] rows = { ModernWidget.WIDGET_HEIGHT };
    int[] cols = { 120, 300 };

    MatrixPanel matrixPanel = new MatrixPanel(rows, cols, ModernWidget.PADDING,
        ModernWidget.PADDING);

    matrixPanel.add(new ModernAutoSizeLabel("Name"));
    matrixPanel.add(new ModernTextBorderPanel(mNameField));
    matrixPanel.setBorder(ModernPanel.LARGE_BORDER);

    box.add(matrixPanel);

    // JPanel buttonPanel = new Panel(new FlowLayout(FlowLayout.LEFT));

    // importButton.setCanvasSize(new Dimension(100,
    // ModernTheme.getInstance().getClass("widget").getInt("height")));
    // exportButton.setCanvasSize(new Dimension(100,
    // ModernTheme.getInstance().getClass("widget").getInt("height")));

    // buttonPanel.add(importButton);
    // buttonPanel.add(exportButton);

    // panel.add(buttonPanel, BorderLayout.PAGE_END);

    setContent(box);
  }
}
