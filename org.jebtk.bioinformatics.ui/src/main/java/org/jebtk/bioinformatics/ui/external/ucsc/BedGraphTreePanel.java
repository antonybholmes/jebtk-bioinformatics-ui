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

import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.jebtk.bioinformatics.ext.ucsc.BedGraph;
import org.jebtk.bioinformatics.ext.ucsc.BedGraphGroupModel;
import org.jebtk.bioinformatics.ext.ucsc.BedGraphGroupsModel;
import org.jebtk.bioinformatics.ext.ucsc.UCSCTrack;
import org.jebtk.bioinformatics.ui.BioInfDialog;
import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.core.tree.TreeNode;
import org.jebtk.core.tree.TreeRootNode;
import org.jebtk.modern.AssetService;
import org.jebtk.modern.button.ModernButton;
import org.jebtk.modern.contentpane.HTabToolbar;
import org.jebtk.modern.dialog.ModernDialogStatus;
import org.jebtk.modern.dialog.ModernMessageDialog;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.io.RecentFilesService;
import org.jebtk.modern.panel.ModernPanel;
import org.jebtk.modern.ribbon.ToolbarButton;
import org.jebtk.modern.scrollpane.ModernScrollPane;
import org.jebtk.modern.scrollpane.ScrollBarPolicy;
import org.jebtk.modern.tree.ModernTree;
import org.jebtk.modern.tree.TreeEventListener;
import org.jebtk.modern.window.ModernWindow;

// TODO: Auto-generated Javadoc
/**
 * The class BedGraphTreePanel.
 */
public class BedGraphTreePanel extends ModernPanel implements ChangeListener {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member tree.
   */
  protected ModernTree<UCSCTrack> mTree = new ModernTree<UCSCTrack>();

  /**
   * The member parent.
   */
  private ModernWindow mParent;

  /**
   * The member model.
   */
  private BedGraphGroupsModel mModel;

  /**
   * The member remove button.
   */
  private ModernButton mRemoveButton = new ToolbarButton(AssetService.getInstance().loadIcon("trash_bw", 16));

  // private ModernButton mClearButton =
  // new ModernButton(UIResources.getInstance().loadIcon("clear", 16));
  // //Ui.MENU_CLEAR);

  /**
   * The class RemoveEvents.
   */
  private class RemoveEvents implements ModernClickListener {

    /*
     * (non-Javadoc)
     * 
     * @see org.jebtk.ui.ui.event.ModernClickListener#clicked(org.jebtk.ui.ui.event.
     * ModernClickEvent)
     */
    @Override
    public void clicked(ModernClickEvent e) {
      for (TreeNode<UCSCTrack> node : mTree.getSelectedNodes()) {
        if (node.getValue() == null) {
          mModel.removeGroup(node.getName());
        }
      }

      createTree();
    }
  }

  /**
   * Instantiates a new bed graph tree panel.
   *
   * @param parent the parent
   * @param model  the model
   */
  public BedGraphTreePanel(ModernWindow parent, BedGraphGroupsModel model) {
    mParent = parent;
    mModel = model;

    setup();

    // setGroups(matrix.getColumnGroups());
  }

  /**
   * Setup.
   */
  private void setup() {

    mModel.addChangeListener(this);

    mTree.setNodeRenderer(new BedGraphTreeNodeRenderer());

    // tree.setNodeSelectionPolicy(SelectionPolicy.SINGLE);
    // tree.addMouseListener(this);
    ModernScrollPane scrollPane = new ModernScrollPane(mTree);
    // scrollPane.setBorder(BORDER);
    scrollPane.setHorizontalScrollBarPolicy(ScrollBarPolicy.NEVER);

    // scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());
    // scrollPane.getViewport().setBackground(Color.WHITE);

    add(scrollPane, BorderLayout.CENTER);

    HTabToolbar box = new HTabToolbar("Files");

    mRemoveButton.setToolTip("Remove BedGraph", "Remove selected BedGraphs.");
    box.add(mRemoveButton);

    // box.add(Box.createHorizontalGlue());
    box.add(ModernPanel.createHGap());

    // mClearButton.setToolTip("Clear BedGraphs", "Remove all BedGraphs.");
    // box.add(mClearButton);

    // box.setBorder(TOP_BOTTOM_BORDER);

    setHeader(box);

    // loadButton.addClickListener(new LoadEvents());
    mRemoveButton.addClickListener(new RemoveEvents());
    // mClearButton.addClickListener(new ClearEvents());
  }

  /**
   * Adds the tree listener.
   *
   * @param l the l
   */
  public void addTreeListener(TreeEventListener l) {
    mTree.addTreeListener(l);
  }

  /**
   * Load.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void load() throws IOException {
    List<Path> files = BioInfDialog.open(mParent).bedgraph().getFiles(RecentFilesService.getInstance().getPwd());

    if (files == null) {
      return;
    }

    BedGraphImportDialog importDialog = new BedGraphImportDialog(mParent, mModel);

    importDialog.setVisible(true);

    if (importDialog.getStatus() == ModernDialogStatus.CANCEL) {
      return;
    }

    String group = importDialog.getGroup();

    for (Path file : files) {
      List<BedGraph> bedGraphs = BedGraph.parse(file);

      for (UCSCTrack graph : bedGraphs) {
        mModel.add(group, graph);
      }
    }

    createTree();
  }

  /**
   * Load.
   *
   * @param file the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void load(Path file) throws IOException {
    List<BedGraph> bedGraphs = BedGraph.parse(file);

    for (UCSCTrack graph : bedGraphs) {
      String group = null;

      // find the closest
      for (TreeNode<UCSCTrack> node : mTree.getFlattenedTree()) {
        if (node.getValue() == null) {
          group = node.getName();
        }

        if (node.equals(mTree.getSelectedNode())) {
          break;
        }
      }

      if (group == null) {
        mModel.add(graph);
      } else {
        mModel.add(group, graph);
      }
    }

    createTree();
  }

  /**
   * Save groups.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void saveGroups() throws IOException {
    UCSCTrack bedGraph = mTree.getSelectedNode().getValue();

    if (bedGraph == null) {
      return;
    }

    Path file = BioInfDialog.save(mParent).bedgraph().getFile(RecentFilesService.getInstance().getPwd());

    if (file == null) {
      return;
    }

    if (Files.exists(file)) {
      ModernDialogStatus status = ModernMessageDialog.createFileReplaceDialog(mParent, file);

      if (status == ModernDialogStatus.CANCEL) {
        saveGroups();

        return;
      }
    }

    BedGraph.write(bedGraph, file);
  }

  /**
   * Creates the tree.
   */
  protected void createTree() {

    TreeRootNode<UCSCTrack> root = new TreeRootNode<UCSCTrack>();

    for (BedGraphGroupModel group : mModel) {
      TreeNode<UCSCTrack> groupNode = new TreeNode<UCSCTrack>(group.getName());

      System.err.println("group " + group.getName());

      for (UCSCTrack bedGraph : group) {
        TreeNode<UCSCTrack> bedNode = new TreeNode<UCSCTrack>(bedGraph.getName(), bedGraph);

        // bedNode.setExpanded(false);

        System.err.println("groupe " + bedGraph.getName());

        groupNode.addChild(bedNode);
      }

      root.addChild(groupNode);
    }

    mTree.setRoot(root);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.event.ChangeListener#changed(org.abh.lib.event.ChangeEvent)
   */
  @Override
  public void changed(ChangeEvent e) {
    createTree();
  }

  /**
   * Gets the selected.
   *
   * @return the selected
   */
  public List<UCSCTrack> getSelected() {
    List<UCSCTrack> ret = new ArrayList<UCSCTrack>();

    if (mTree.getSelectedNodes().size() == 0) {
      return ret;
    }

    for (TreeNode<UCSCTrack> node : mTree.getSelectedNodes()) {
      UCSCTrack bedGraph = node.getValue();

      System.err.println("selected " + bedGraph.getName());

      if (bedGraph != null) {
        ret.add(bedGraph);
      }
    }

    return ret;
  }
}
