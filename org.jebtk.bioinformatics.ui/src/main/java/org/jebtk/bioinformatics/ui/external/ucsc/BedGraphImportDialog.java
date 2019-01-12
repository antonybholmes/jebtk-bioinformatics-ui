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

import javax.swing.Box;

import org.jebtk.bioinformatics.ext.ucsc.BedGraphGroupModel;
import org.jebtk.bioinformatics.ext.ucsc.BedGraphGroupsModel;
import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.UI;
import org.jebtk.modern.button.ModernCheckBox;
import org.jebtk.modern.combobox.ModernComboBox;
import org.jebtk.modern.dialog.ModernDialogTaskWindow;
import org.jebtk.modern.panel.MatrixPanel;
import org.jebtk.modern.panel.ModernPanel;
import org.jebtk.modern.text.ModernAutoSizeLabel;
import org.jebtk.modern.widget.ModernWidget;
import org.jebtk.modern.window.ModernWindow;

/**
 * Allows a matrix group to be edited.
 *
 * @author Antony Holmes
 */
public class BedGraphImportDialog extends ModernDialogTaskWindow {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member combo groups.
   */
  private ModernComboBox mComboGroups = new ModernComboBox();

  /**
   * The check assign new groups.
   */
  private ModernCheckBox checkAssignNewGroups = new ModernCheckBox(
      "Assign to new groups");

  /**
   * The member model.
   */
  private BedGraphGroupsModel mModel;

  /**
   * The member group.
   */
  private String mGroup = null;

  /**
   * Instantiates a new bed graph import dialog.
   *
   * @param parent the parent
   * @param model the model
   */
  public BedGraphImportDialog(ModernWindow parent, BedGraphGroupsModel model) {
    super(parent);

    mModel = model;

    setup();

    createUi();
  }

  /**
   * Setup.
   */
  private void setup() {
    setTitle("BedGraph Import");

    if (mModel.size() == 0) {
      mComboGroups.addMenuItem("Default");
    } else {
      for (BedGraphGroupModel group : mModel) {
        mComboGroups.addMenuItem(group.getName());
      }
    }

    mComboGroups.setEditable(true);

    setSize(new Dimension(500, 200));

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

    matrixPanel.add(new ModernAutoSizeLabel("Select group"));
    matrixPanel.add(mComboGroups);
    matrixPanel.add(new ModernComponent());
    matrixPanel.add(checkAssignNewGroups);

    matrixPanel.setBorder(ModernPanel.LARGE_BORDER);

    box.add(matrixPanel);

    setContent(box);
  }

  /**
   * Edits the.
   */
  private void edit() {
    mGroup = mComboGroups.getText();
  }

  /**
   * Gets the group.
   *
   * @return the group
   */
  public String getGroup() {
    return mGroup;
  }

  /**
   * Gets the assign to new groups.
   *
   * @return the assign to new groups
   */
  public boolean getsignToNewGroups() {
    return checkAssignNewGroups.isSelected();
  }
}
