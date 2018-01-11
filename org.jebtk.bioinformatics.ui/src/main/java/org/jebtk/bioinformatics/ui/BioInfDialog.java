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

import java.awt.Frame;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.jebtk.bioinformatics.ui.external.genepattern.GctGuiFileFilter;
import org.jebtk.bioinformatics.ui.external.ucsc.BedGraphGuiFileFilter;
import org.jebtk.bioinformatics.ui.external.ucsc.BedGuiFileFilter;
import org.jebtk.bioinformatics.ui.filters.EstGuiFileFilter;
import org.jebtk.bioinformatics.ui.filters.FastaGuiFileFilter;
import org.jebtk.bioinformatics.ui.filters.MatrixFilesGuiFileExtFilter;
import org.jebtk.bioinformatics.ui.filters.MotifGuiFileFilter;
import org.jebtk.bioinformatics.ui.filters.MotifPwmGuiFileFilter;
import org.jebtk.bioinformatics.ui.filters.ResGuiFileFilter;
import org.jebtk.math.ui.external.microsoft.XlsxGuiFileFilter;
import org.jebtk.modern.io.AllGuiFilesFilter;
import org.jebtk.modern.io.FileDialog;
import org.jebtk.modern.io.FileDialog.OpenDialog;
import org.jebtk.modern.io.FileDialog.PathsSelection;
import org.jebtk.modern.io.FileDialog.SaveDialog;
import org.jebtk.modern.io.FileDialog.SaveFileSelection;
import org.jebtk.modern.io.JpgGuiFileFilter;
import org.jebtk.modern.io.PdfGuiFileFilter;
import org.jebtk.modern.io.PngGuiFileFilter;
import org.jebtk.modern.io.RecentFilesService;
import org.jebtk.modern.io.SvgGuiFileFilter;
import org.jebtk.modern.io.TsvGuiFileFilter;

// TODO: Auto-generated Javadoc
/**
 * The class BioInfDialog.
 */
public class BioInfDialog {

  /**
   * Save file.
   *
   * @param parent the parent
   * @return the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static Path saveFile(Frame parent) throws IOException {
    return saveMatrixFile(parent, RecentFilesService.getInstance().getPwd());
  }

  /**
   * Save matrix file.
   *
   * @param parent the parent
   * @param pwd the pwd
   * @return the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static Path saveMatrixFile(Frame parent, Path pwd) throws IOException {
    return saveMatrixFile(parent, pwd, FileDialog.EMPTY_FILE);
  }

  /**
   * Save matrix file.
   *
   * @param parent the parent
   * @param pwd the pwd
   * @param suggestedFile the suggested file
   * @return the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static Path saveMatrixFile(Frame parent, Path pwd, Path suggestedFile)
      throws IOException {
    return FileDialog.save(parent)
        .filter(new XlsxGuiFileFilter(),
            new TsvGuiFileFilter(),
            new GctGuiFileFilter(),
            new EstGuiFileFilter())
        .suggested(suggestedFile).getFile(pwd);
  }

  /**
   * Save fasta file.
   *
   * @param parent the parent
   * @param pwd the pwd
   * @return the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static Path saveFastaFile(Frame parent, Path pwd) throws IOException {
    return FileDialog.save(parent).filter(new FastaGuiFileFilter())
        .getFile(pwd);
  }

  /**
   * Save bed graph file.
   *
   * @param parent the parent
   * @param pwd the pwd
   * @return the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static Path saveBedGraphFile(Frame parent, Path pwd)
      throws IOException {
    return FileDialog.save(parent).filter(new BedGraphGuiFileFilter())
        .getFile(pwd);
  }

  /**
   * Save bed file.
   *
   * @param parent the parent
   * @param pwd the pwd
   * @return the path
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static Path saveBedFile(Frame parent, Path pwd) throws IOException {
    return FileDialog.save(parent).filter(new BedGuiFileFilter()).getFile(pwd);
  }

  /**
   * Open matrix file.
   *
   * @param parent the parent
   * @param pwd the pwd
   * @return the file
   */
  public static Path openMatrixFile(Frame parent, Path pwd) {
    return FileDialog.openFile(parent,
        pwd,
        new AllGuiFilesFilter(),
        new MatrixFilesGuiFileExtFilter(),
        new XlsxGuiFileFilter(),
        new TsvGuiFileFilter(),
        new GctGuiFileFilter(),
        new ResGuiFileFilter(),
        new EstGuiFileFilter(),
        new BedGuiFileFilter(),
        new BedGraphGuiFileFilter());
  }

  /**
   * Open motif file.
   *
   * @param parent the parent
   * @param pwd the pwd
   * @return the file
   */
  public static Path openMotifFile(Frame parent, Path pwd) {
    List<Path> files = openMotifFiles(parent, pwd);

    if (files == null) {
      return null;
    }

    return files.get(0);
  }

  /**
   * Open motif files.
   *
   * @param parent the parent
   * @param pwd the pwd
   * @return the list
   */
  public static List<Path> openMotifFiles(Frame parent, Path pwd) {
    return FileDialog.openFiles(parent,
        pwd,
        new MotifGuiFileFilter(),
        new MotifPwmGuiFileFilter());
  }

  //
  // Functional approach
  //

  //
  // Open
  //

  /**
   * The Class BioInfOpenDialog.
   */
  public static class BioInfOpenDialog {

    /** The m open. */
    private OpenDialog mOpen;

    /**
     * Instantiates a new bio inf open dialog.
     *
     * @param frame the frame
     */
    private BioInfOpenDialog(Frame frame) {
      mOpen = FileDialog.open(frame);
    }

    /**
     * Bed.
     *
     * @return the paths selection
     */
    public PathsSelection bed() {
      return mOpen.filter(new BedGuiFileFilter());
    }

    /**
     * Bedgraph.
     *
     * @return the paths selection
     */
    public PathsSelection bedgraph() {
      return mOpen.filter(new BedGraphGuiFileFilter());
    }

    /**
     * Bed and bedgraph.
     *
     * @return the paths selection
     */
    public PathsSelection bedAndBedgraph() {
      return mOpen.filter(new BedGuiFileFilter(), new BedGraphGuiFileFilter());
    }
  }

  /**
   * Open.
   *
   * @param frame the frame
   * @return the bio inf open dialog
   */
  public static BioInfOpenDialog open(Frame frame) {
    return new BioInfOpenDialog(frame);
  }

  //
  // Save
  //

  /**
   * The Class BioInfSaveDialog.
   */
  public static class BioInfSaveDialog {

    /** The m save. */
    private SaveDialog mSave;

    /**
     * Instantiates a new bio inf save dialog.
     *
     * @param frame the frame
     */
    private BioInfSaveDialog(Frame frame) {
      mSave = FileDialog.save(frame);
    }

    /**
     * Bed.
     *
     * @return the save file selection
     */
    public SaveFileSelection bed() {
      return mSave.filter(new BedGuiFileFilter());
    }

    /**
     * Bedgraph.
     *
     * @return the save file selection
     */
    public SaveFileSelection bedgraph() {
      return mSave.filter(new BedGraphGuiFileFilter());
    }

    /**
     * Fasta.
     *
     * @return the save file selection
     */
    public SaveFileSelection fasta() {
      return mSave.filter(new FastaGuiFileFilter());
    }

    /**
     * Image.
     *
     * @return the save file selection
     */
    public SaveFileSelection image() {
      return mSave.filter(new SvgGuiFileFilter(),
          new PngGuiFileFilter(),
          new PdfGuiFileFilter(),
          new JpgGuiFileFilter());
    }
  }

  /**
   * Save.
   *
   * @param frame the frame
   * @return the bio inf save dialog
   */
  public static BioInfSaveDialog save(Frame frame) {
    return new BioInfSaveDialog(frame);
  }
}
