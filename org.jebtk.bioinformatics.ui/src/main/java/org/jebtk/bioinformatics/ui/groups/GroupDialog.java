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
package org.jebtk.bioinformatics.ui.groups;

import java.awt.Color;
import java.util.List;

import javax.swing.Box;

import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.text.TextUtils;
import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.UI;
import org.jebtk.modern.button.ModernCheckSwitch;
import org.jebtk.modern.dialog.ModernDialogTaskWindow;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.panel.VBox;
import org.jebtk.modern.widget.ModernTwoStateWidget;
import org.jebtk.modern.window.ModernWindow;

/**
 * Allows a region groups to be edited.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class GroupDialog extends ModernDialogTaskWindow
    implements ModernClickListener {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member check unique.
   */
  private ModernTwoStateWidget mCheckUnique = new ModernCheckSwitch(
      "Make unique", true);

  /**
   * The member check remove na.
   */
  private ModernTwoStateWidget mCheckRemoveNA = new ModernCheckSwitch(
      "Remove n/a", true);

  /** The m check ignore empty. */
  private ModernTwoStateWidget mCheckIgnoreEmpty = new ModernCheckSwitch(
      "Ignore empty lines", true);

  /**
   * The member panel.
   */
  private GroupEditPanel mPanel;

  /**
   * Instantiates a new group dialog.
   *
   * @param parent the parent
   * @param group the group
   */
  public GroupDialog(ModernWindow parent, Group group) {
    super(parent);

    mPanel = new GroupEditPanel(parent, group);

    setTitle("Group Editor", group.getName());

    setup();

    createUi();
  }

  /**
   * Setup.
   */
  private void setup() {
    setSize(640, 540);

    UI.centerWindowToScreen(this);
  }

  /**
   * Creates the ui.
   */
  private final void createUi() {
    ModernComponent panel = new ModernComponent();

    panel.setBody(mPanel);

    Box box = VBox.create();
    box.add(UI.createVGap(10));
    box.add(mCheckUnique);
    box.add(mCheckRemoveNA);
    box.add(mCheckIgnoreEmpty);
    panel.setFooter(box);

    setCard(panel);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.Component#getName()
   */
  public String getName() {
    return mPanel.getName();
  }

  /**
   * Gets the color.
   *
   * @return the color
   */
  public Color getColor() {
    return mPanel.getColor();
  }

  /**
   * Gets the entries.
   *
   * @return the entries
   */
  public List<String> getEntries() {
    List<String> ret;

    if (mCheckUnique.isSelected()) {
      ret = CollectionUtils.uniquePreserveOrder(mPanel.getEntries());
    } else {
      ret = mPanel.getEntries();
    }

    if (mCheckRemoveNA.isSelected()) {
      ret = TextUtils.removeNA(ret);
    }

    if (mCheckIgnoreEmpty.isSelected()) {
      ret = TextUtils.removeEmptyElements(ret);
    }

    return ret;
  }
}
