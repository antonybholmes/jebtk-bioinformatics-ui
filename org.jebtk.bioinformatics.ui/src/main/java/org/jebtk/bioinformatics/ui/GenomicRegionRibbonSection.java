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
package org.jebtk.bioinformatics.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Timer;

import org.jebtk.bioinformatics.genomic.Chromosome;
import org.jebtk.bioinformatics.genomic.GenesService;
import org.jebtk.bioinformatics.genomic.GenomeService;
import org.jebtk.bioinformatics.genomic.GenomicRegion;
import org.jebtk.bioinformatics.genomic.GenomicRegionModel;
import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.modern.UI;
import org.jebtk.modern.UIService;
import org.jebtk.modern.button.ModernButton;
import org.jebtk.modern.combobox.ModernComboBox;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.graphics.icons.MinusVectorIcon;
import org.jebtk.modern.graphics.icons.PlusVectorIcon;
import org.jebtk.modern.ribbon.Ribbon;
import org.jebtk.modern.ribbon.RibbonButton;
import org.jebtk.modern.ribbon.RibbonSection;
import org.jebtk.modern.ribbon.RibbonStripContainer;
import org.jebtk.modern.widget.ModernWidget;

/**
 * Allows user to select a color map.
 *
 * @author Antony Holmes Holmes
 *
 */
public class GenomicRegionRibbonSection extends RibbonSection {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The constant SHIFT.
   */
  private static final double SHIFT = 0.05;

  /**
   * The constant TIMER_DELAY.
   */
  private static final int TIMER_DELAY = 10;

  /**
   * The constant LONG_TIMER_DELAY.
   */
  private static final int LONG_TIMER_DELAY = 200;

  /**
   * The constant LOCATION_SIZE.
   */
  private static final Dimension LOCATION_SIZE = new Dimension(200,
      ModernWidget.WIDGET_HEIGHT);

  
  
  /**
   * The member location field.
   */
  protected ModernComboBox mLocationField = new ModernComboBox();

  // private ModernTextField m5pExtField = new ModernTextField("0");

  // private ModernTextField m3pExtField = new ModernTextField("0");

  /**
   * The member model.
   */
  private GenomicRegionModel mModel;

  /**
   * The member zoom in button.
   */
  private ModernButton mZoomInButton = new RibbonButton(
      UIService.getInstance().loadIcon(PlusVectorIcon.class, 16));

  /**
   * The member zoom out button.
   */
  private ModernButton mZoomOutButton = new RibbonButton(
      UIService.getInstance().loadIcon(MinusVectorIcon.class, 16));

  /**
   * The member move left button.
   */
  private ModernButton mMoveLeftButton = new RibbonButton(
      UIService.getInstance().loadIcon("left_arrow", 16));

  /**
   * The member move right button.
   */
  private ModernButton mMoveRightButton = new RibbonButton(
      UIService.getInstance().loadIcon("right_arrow", 16));

  /**
   * The member genome model.
   */
  protected GenomeModel mGenomeModel;

  /**
   * The member used.
   */
  private Set<String> mUsed = new HashSet<String>();

  /**
   * The class KeyEvents.
   */
  private class KeyEvents implements KeyListener {

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    @Override
    public void keyPressed(KeyEvent e) {
      // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    @Override
    public void keyReleased(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        try {
          change();
        } catch (ParseException e1) {
          e1.printStackTrace();
        }
      }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    @Override
    public void keyTyped(KeyEvent e) {
      // TODO Auto-generated method stub

    }

  }

  /**
   * The class ChangeEvents.
   */
  private class ChangeEvents implements ChangeListener {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.abh.lib.event.ChangeListener#changed(org.abh.lib.event.ChangeEvent)
     */
    @Override
    public void changed(ChangeEvent e) {
      try {
        change();
      } catch (ParseException e1) {
        e1.printStackTrace();
      }
    }
  }

  /**
   * The class LocationEvents.
   */
  private class LocationEvents implements ModernClickListener {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.jebtk.ui.ui.event.ModernClickListener#clicked(org.jebtk.ui.ui.event.
     * ModernClickEvent)
     */
    @Override
    public void clicked(ModernClickEvent e) {
      try {
        change();
      } catch (ParseException e1) {
        e1.printStackTrace();
      }
    }
  }

  /**
   * The class ZoomInEvents.
   */
  private class ZoomInEvents extends MouseAdapter implements ActionListener {

    /**
     * The member timer.
     */
    private Timer mTimer;

    /**
     * Instantiates a new zoom in events.
     */
    public ZoomInEvents() {
      mTimer = new Timer(LONG_TIMER_DELAY, this);
      mTimer.setInitialDelay(0);
      mTimer.setRepeats(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
      mTimer.start();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e) {
      mTimer.stop();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        zoomIn();
      } catch (ParseException e1) {
        e1.printStackTrace();
      }
    }
  }

  /**
   * The class ZoomOutEvents.
   */
  private class ZoomOutEvents extends MouseAdapter implements ActionListener {

    /**
     * The member timer.
     */
    private Timer mTimer;

    /**
     * Instantiates a new zoom out events.
     */
    public ZoomOutEvents() {
      mTimer = new Timer(LONG_TIMER_DELAY, this);
      mTimer.setInitialDelay(0);
      mTimer.setRepeats(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
      mTimer.start();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e) {
      mTimer.stop();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        zoomOut();
      } catch (ParseException e1) {
        e1.printStackTrace();
      }
    }
  }

  /**
   * The class MoveLeftEvents.
   */
  private class MoveLeftEvents extends MouseAdapter implements ActionListener {

    /**
     * The member timer.
     */
    private Timer mTimer;

    /**
     * Instantiates a new move left events.
     */
    public MoveLeftEvents() {
      mTimer = new Timer(TIMER_DELAY, this);
      mTimer.setInitialDelay(0);
      mTimer.setRepeats(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
      mTimer.start();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e) {
      mTimer.stop();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        moveLeft();
      } catch (ParseException e1) {
        e1.printStackTrace();
      }
    }
  }

  /**
   * The class MoveRightEvents.
   */
  private class MoveRightEvents extends MouseAdapter implements ActionListener {

    /**
     * The member timer.
     */
    private Timer mTimer;

    /**
     * Instantiates a new move right events.
     */
    public MoveRightEvents() {
      mTimer = new Timer(TIMER_DELAY, this);
      mTimer.setInitialDelay(0);
      mTimer.setRepeats(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
      mTimer.start();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e) {
      mTimer.stop();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        moveRight();
      } catch (ParseException e1) {
        e1.printStackTrace();
      }
    }
  }

  /**
   * The class RefreshEvents.
   */
  private class RefreshEvents implements ChangeListener {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.abh.lib.event.ChangeListener#changed(org.abh.lib.event.ChangeEvent)
     */
    @Override
    public void changed(ChangeEvent e) {
      refresh();
    }

  }

  /**
   * Instantiates a new genomic region ribbon section.
   *
   * @param ribbon the ribbon
   * @param model the model
   * @param genomeModel the genome model
   */
  public GenomicRegionRibbonSection(Ribbon ribbon, GenomicRegionModel model,
      GenomeModel genomeModel) {
    super(ribbon, "Region");

    mModel = model;
    mGenomeModel = genomeModel;

    RibbonStripContainer box = new RibbonStripContainer();

    box.add(mZoomInButton);
    box.add(mZoomOutButton);
    box.add(createHGap());

    UI.setSize(mLocationField, LOCATION_SIZE);

    box.add(mLocationField);

    box.add(createHGap());
    box.add(mMoveLeftButton);
    box.add(mMoveRightButton);

    add(box);

    RefreshEvents ce = new RefreshEvents();

    mLocationField.addKeyListener(new KeyEvents());
    mLocationField.addClickListener(new LocationEvents());

    mModel.addChangeListener(ce);
    mGenomeModel.addChangeListener(ce);

    mZoomInButton.addMouseListener(new ZoomInEvents());
    mZoomOutButton.addMouseListener(new ZoomOutEvents());

    mMoveLeftButton.addMouseListener(new MoveLeftEvents());
    mMoveRightButton.addMouseListener(new MoveRightEvents());

    mLocationField.setEditable(true);

    refresh();
  }

  /**
   * Refresh.
   */
  private void refresh() {
    mLocationField.setText(mModel.get().getFormattedLocation());
  }

  /**
   * Change.
   *
   * @throws ParseException the parse exception
   */
  private void change() throws ParseException {
    GenomicRegion region = parse(mGenomeModel.get());

    if (region != null) {
      // Add the location before updating the model, since the model
      // refresh will change gene symbols to location and we want to
      // store what the user typed, not what we modified it to.

      String name = mLocationField.getText();

      if (!mUsed.contains(name)) {
        mLocationField.addScrollMenuItem(name);
        mUsed.add(name);
      }

      mModel.set(region);
    }
  }

  /**
   * Parses the.
   *
   * @return the genomic region
   * @throws ParseException the parse exception
   */
  protected GenomicRegion parse(String genome) {
    String text = mLocationField.getText().toLowerCase();

    GenomicRegion region = null;

    if (GenomicRegion.CHR_ONLY_PATTERN.matcher(text).matches()) {
      // use the whole chromosome

      Chromosome chromosome = GenomeService.instance().chr(genome, text);

      int size = chromosome.getSize();

      region = new GenomicRegion(chromosome, 1, size);

    } else if (text.startsWith("chr")) { // remove commas
      region = GenomicRegion.parse(genome, mLocationField.getText());
 
      int size = region.getChr().getSize();

      region = new GenomicRegion(region.getChr(),
          Math.max(1, region.getStart()), Math.min(region.getEnd(), size));

    } else {
      // assume its a gene

      region = GenesService.getInstance().getGenes(genome)
          .getGene(text);
    }

    return region;
  }

  /**
   * Zoom in.
   *
   * @throws ParseException the parse exception
   */
  private void zoomIn() throws ParseException {
    zoom(0.25);
  }

  /**
   * Zoom out.
   *
   * @throws ParseException the parse exception
   */
  private void zoomOut() throws ParseException {
    zoom(4);
  }

  /**
   * Zoom.
   *
   * @param scale the scale
   * @throws ParseException the parse exception
   */
  private void zoom(double scale) throws ParseException {
    GenomicRegion region = parse(mGenomeModel.get());

    if (region == null) {
      return;
    }

    int size = region.getChr().getSize();

    int midPoint = (region.getStart() + region.getEnd()) / 2;

    int d = (int) ((region.getEnd() - region.getStart()) * scale);
    int d2 = Math.max(d / 2, 1);

    int start = (int) Math.max(Math.min(midPoint - d2, size), 1);
    int end = (int) Math.max(Math.min(midPoint + d2, size), 1);

    GenomicRegion newRegion = new GenomicRegion(region.getChr(), start, end);

    mModel.set(newRegion);
  }

  /**
   * Move left.
   *
   * @throws ParseException the parse exception
   */
  private void moveLeft() throws ParseException {
    move(-SHIFT);
  }

  /**
   * Move right.
   *
   * @throws ParseException the parse exception
   */
  private void moveRight() throws ParseException {
    move(SHIFT);
  }

  /**
   * Move.
   *
   * @param p the p
   * @throws ParseException the parse exception
   */
  private void move(double p) throws ParseException {
    GenomicRegion region = parse(mGenomeModel.get());

    if (region == null) {
      return;
    }

    int shift = (int) (region.getLength() * p);

    GenomicRegion newRegion = GenomicRegion.shift(region,
        shift);

    mModel.set(newRegion);
  }
}
