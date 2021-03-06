package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.Document;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import model.Accord;
import model.CreateurHTML.Memento;
import model.Frets;
import view.JFontChooser;

/**
 * Le gestionnaire IHM paramêtre les éléments de l'ihm et lui affecte les actions
 * et les comportements qu'il doit avoir. Il met également en page les éléments 
 * dans le layout.
 * @author Yann LERJEN & gregoryangeloz & Yannick Lagger
 */
public class GestionnaireIhm {
        
    private GestionnairePartitions gestPartitions;
    private boolean debug = false;
    /**
     * menuBar est la barre de menu qui est en entête de la fenêtre
     */
    public JMenuBar menuBar;
    private JToolBar barreOutils;
    private JMenu menuFile;
    private JMenuItem menuItemNew;
    private JMenuItem menuItemOpen;
    private JMenuItem menuItemSave;
    private JMenuItem menuItemSaveAs;
    private JMenuItem menuItemExit;
    private JMenu menuEdit;
    private JMenuItem redoItem;
    private JMenuItem undoItem;
    private JMenuItem cutItem;
    private JMenuItem copyItem;
    private JMenuItem pasteItem;
    private JMenuItem menuItemPrint;
    private JMenuItem leftAlignItem;
    private JMenuItem centerAlignItem;
    private JMenuItem rightAlignItem;
    private JMenu menuExport;
    private JMenuItem menuItemPdf;
    private JMenuItem menuItemHtml;
    private JMenuItem menuItemChordPro;
    private JMenu menuView;
    private JMenu menuFont;
    private JMenu menuAlign;
    private JMenu fontSizeMenu;
    private JMenuItem menuItemEditMode;
    private JMenuItem menuItemDisplayMode;    
    private JButton alignCenter;
    private JButton leftAlign;
    private JButton rightAlign;
    private JButton cut;
    private JButton past;
    private JButton copy;
    private JMenu menuHelp;
    private JMenu menuSongBook;
    private JMenuItem menuItemOpenSongBook;
    private JMenuItem menuItemNewSongBook;
    private JMenu menuLangage;
    private JMenuItem menuLangageFrench;
    private JMenuItem menuLangageEnglish;
    private JMenuItem menuItemAbout;
    /**
     * Est le panneau avec des ascenseurs de scroll qui contient le panelControl
     */
    public JScrollPane scrollpaneControl;
    private JPanel outerPanel;
    /**
     * Est le panneau de controle qui contient les boutons de control de la partition
     */
    public JPanel panelControl;
    /**
     * Est le panneau contenant la partition
     */
    public JPanel panelPartition;
    /**
     * Est le panneau de pied de page
     */
    public JPanel panelFooter;
    private JButton btnChangeFontLyrics;
    private JButton btnChangeFontChord;    
    private String[] transposChoix;
    private JComboBox<String> cmbbxTransposition;
    private JCheckBox chckbxDisplayLyrics;
    private JCheckBox chckbxDisplayChord;
    private JRadioButton radioChordImg;
    private JRadioButton radioChordTxt;  
    private JLabel label_ctrl_title1;
    private JLabel label_ctrl_title2;
    private JLabel label_ctrl_section1;
    private JLabel label_ctrl_section2;
    private JLabel label_ctrl_section3;
    private JLabel label_ctrl_section4;

    /**
     * Créé le gestionnaire d'Ihm qui gèrera les actions et comportements de l'interface
     * graphique. Le gestionnaire de partition passé sera également placé dans la fenêtre.
     * @param gestPartitions est le gestionnaire de partition qui permettra l'affichage de
     * la partition.
     */
    public GestionnaireIhm(GestionnairePartitions gestPartitions) {
        this.gestPartitions = gestPartitions;
        initComponents();
        createMenu();
        createJToolBar();
        createPanelControl();
        createPanelPartition();
        createPanelFooter();
        setEvent();
        loadLanguageMenuAndButton();
        /** On démarre un nouveau document */
        this.gestPartitions.startNewDocument();
    }

    private void initComponents() {
//        this.fontChooser = new JFontChooser(); TODO supprimer ou enlever le commentaire
        
        //menu        
        this.menuBar = new JMenuBar();
        this.menuFile = new JMenu();
        this.menuItemNew = new JMenuItem();
        this.menuFile.add(this.menuItemNew);
        this.menuItemOpen = new JMenuItem();
        this.menuFile.add(this.menuItemOpen);
        this.menuItemSave = new JMenuItem();
        this.menuFile.add(this.menuItemSave);
        this.menuItemSaveAs = new JMenuItem();
        this.menuItemPrint = new JMenuItem();        
        this.menuFile.add(this.menuItemSaveAs);
        this.menuFile.add(this.menuItemPrint);
        this.menuItemExit = new JMenuItem();
        this.menuEdit = new JMenu();
        /** On ajoute les actions undo / redo / cut / copy / paste au menu edition */
        this.undoItem = new JMenuItem(gestPartitions.getUndoAction());
        this.redoItem = new JMenuItem(gestPartitions.getRedoAction());
        this.cutItem = new JMenuItem(gestPartitions.getCutAction());
        this.cutItem.setEnabled(false);
        this.copyItem = new JMenuItem(gestPartitions.getCopyAction());
        this.copyItem.setEnabled(false);
        this.pasteItem = new JMenuItem(gestPartitions.getPasteAction());
        this.pasteItem.setEnabled(false);
        this.menuEdit.add(undoItem);
        this.menuEdit.add(redoItem);
        this.menuEdit.add(cutItem);
        this.menuEdit.add(copyItem);
        this.menuEdit.add(pasteItem);
        
        this.menuAlign = new JMenu();
        this.leftAlignItem = new JMenuItem(gestPartitions.getLeftAlignAction());
        this.centerAlignItem = new JMenuItem(gestPartitions.getCenterAlignAction());
        this.rightAlignItem = new JMenuItem(gestPartitions.getRighAlignAction());
        this.menuAlign.add(leftAlignItem);
        this.menuAlign.add(centerAlignItem);
        this.menuAlign.add(rightAlignItem);

        this.menuFont = new JMenu();
        /** On cr��e un menu permettant de changer la taille de la police sur le texte sélectionné */
        this.fontSizeMenu = new JMenu();
        this.menuFont.add(fontSizeMenu);

        int[] fontSizes = {6, 8, 10, 12, 14, 16, 20, 24, 32};
        for (int i = 0; i < fontSizes.length; i++) {
            if (debug) {
                System.out.println(fontSizes[i]);
            }
            JMenuItem nextSizeItem = new JMenuItem(String.valueOf(fontSizes[i]));
            nextSizeItem.setAction(new StyledEditorKit.FontSizeAction(String.valueOf(fontSizes[i]), fontSizes[i]));
            fontSizeMenu.add(nextSizeItem);
        }

        this.menuView = new JMenu();
        this.menuItemEditMode = new JRadioButtonMenuItem();

        this.menuItemDisplayMode = new JRadioButtonMenuItem();
        this.menuItemDisplayMode.setSelected(true);
        this.menuSongBook = new JMenu();
        this.menuItemNewSongBook = new JMenuItem(GestionnaireSongBook.rendGestionnaireSongBookUnique().rendActionNew());
        this.menuSongBook.add(this.menuItemNewSongBook);
        this.menuItemOpenSongBook = new JMenuItem(GestionnaireSongBook.rendGestionnaireSongBookUnique().rendActionOpen());
        this.menuSongBook.add(this.menuItemOpenSongBook);

        /** gestion des langues **/
        this.menuLangage = new JMenu();
        this.menuLangageFrench = new JMenuItem();
        this.menuLangageEnglish = new JMenuItem(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_langage_english"));
        this.menuLangage.add(this.menuLangageFrench);
        this.menuLangage.add(this.menuLangageEnglish);

        this.menuExport = new JMenu(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_export"));
        this.menuItemPdf = new JMenuItem(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("export_PDF"));
        this.menuItemHtml = new JMenuItem(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("export_HTML"));
        this.menuItemChordPro = new JMenuItem(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("export_ChordPro"));
        this.menuHelp = new JMenu(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_help"));
        this.menuItemAbout = new JMenuItem(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_about"));
        //panneau des boutons de contr�le  
        this.panelControl = new JPanel();
        this.outerPanel= new JPanel(new FlowLayout());
        this.scrollpaneControl = new JScrollPane(this.outerPanel);
        //Labels du panneau de contr�le        
        this.label_ctrl_title1 = new JLabel();
        this.label_ctrl_title2 = new JLabel();
        this.label_ctrl_section1 = new JLabel();
        this.label_ctrl_section2 = new JLabel();
        this.label_ctrl_section3 = new JLabel();
        this.label_ctrl_section4 = new JLabel();
        //Bouton du panneau de contr�le
        this.btnChangeFontChord = new JButton();
        this.btnChangeFontChord.setPreferredSize(new Dimension(150, 30));
        this.btnChangeFontLyrics = new JButton();
        this.btnChangeFontLyrics.setPreferredSize(new Dimension(150, 30));
        this.chckbxDisplayChord = new JCheckBox();
        this.chckbxDisplayChord.setSelected(true);
        this.chckbxDisplayLyrics = new JCheckBox();
        this.chckbxDisplayLyrics.setSelected(true);
        ButtonGroup grpButton2 = new ButtonGroup();
        this.radioChordImg = new JRadioButton();
        this.radioChordTxt = new JRadioButton();      
        this.radioChordTxt.setSelected(true);
        grpButton2.add(this.radioChordImg);
        grpButton2.add(this.radioChordTxt);  
        this.transposChoix = new String[7];
        transposChoix[0] = "+3 " + GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("halfton");
        transposChoix[1] = "+2 " + GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("halfton");
        transposChoix[2] = "+1 " + GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("halfton");
        transposChoix[3] = "-";
        transposChoix[4] = "-1 " + GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("halfton");
        transposChoix[5] = "-2 " + GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("halfton");
        transposChoix[6] = "-3 " + GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("halfton");
        this.cmbbxTransposition = new JComboBox(transposChoix);
        this.cmbbxTransposition.setSelectedItem("-");
        //panneau d'affichage des partitions
        this.panelPartition = new JPanel();
        //panneau des informations en pied de page
        this.panelFooter = new JPanel(new GridLayout(1, 4));

        //panneau des informations en pied de page
        this.panelFooter = new JPanel(new GridLayout(1, 4));
              
    }

    private void createMenu() {
        //cr��ation de la barre de menu
        //menu fichier
        this.menuBar.add(this.menuFile);
        this.menuFile.add(this.menuItemExit);
        //menu edition
        this.menuBar.add(this.menuEdit);
        //menu police
        this.menuBar.add(this.menuFont);

        /**
         * Menu align
         */
        this.menuBar.add(this.menuAlign);

        //menu affichage
        ButtonGroup myGroup = new ButtonGroup();
        this.menuView.add(menuItemEditMode);
        myGroup.add(menuItemEditMode);
        this.menuView.add(menuItemDisplayMode);
        myGroup.add(menuItemDisplayMode);
        this.menuBar.add(this.menuView);
        //menu SongBook
        this.menuBar.add(this.menuSongBook);
        //menu langue
        this.menuBar.add(this.menuLangage);
        //menu export
        this.menuBar.add(this.menuExport);
        this.menuExport.add(this.menuItemPdf);
        this.menuExport.add(this.menuItemHtml);
        this.menuExport.add(this.menuItemChordPro);
        //menu aide
        this.menuBar.add(this.menuHelp);
        this.menuHelp.add(menuItemAbout);
    }

    /**
     * Crée la barre d'outil JToolBar qui contient les actions d'alignements du text, ainsi que les 
     * actions couper, copier et coller
     */
    private void createJToolBar() {
        this.barreOutils = new JToolBar();
        this.alignCenter = new JButton(gestPartitions.getCenterAlignAction());
        alignCenter.setIcon(createImageIcon("align_center_icon&24"));
        this.alignCenter.setToolTipText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("align_center"));
        this.alignCenter.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("align_center"));
        this.leftAlign = new JButton(gestPartitions.getLeftAlignAction());
        leftAlign.setToolTipText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("align_left"));
        leftAlign.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("align_left"));
        leftAlign.setIcon(createImageIcon("align_left_icon&24"));
        this.rightAlign = new JButton(gestPartitions.getRighAlignAction());
        rightAlign.setToolTipText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("align_right"));
        rightAlign.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("align_right"));
        rightAlign.setIcon(createImageIcon("align_right_icon&24"));        
        barreOutils.add(leftAlign);
        barreOutils.add(alignCenter);
        barreOutils.add(rightAlign);
        barreOutils.addSeparator();
        this.copy = new JButton(gestPartitions.getCopyAction());
        copy.setToolTipText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("copy"));
        copy.setEnabled(false);
        copy.setIcon(createImageIcon("clipboard_copy_icon&24"));
        copy.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("copy"));
        this.cut = new JButton(gestPartitions.getCutAction());
        cut.setToolTipText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("cut"));
        cut.setEnabled(false);
        cut.setIcon(createImageIcon("clipboard_cut_icon&24"));
        cut.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("cut"));
        this.past = new JButton(gestPartitions.getPasteAction());
        past.setToolTipText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("paste"));
        past.setEnabled(false);
        past.setIcon(createImageIcon("clipboard_past_icon&24"));
        past.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("paste"));
        barreOutils.add(copy);
        barreOutils.add(cut);
        barreOutils.add(past);
    }

    /**
     * Retourne la barre d'outils de la fenêtre
     * @return la JToolBar qui est la barre d'outil
     */
    public JToolBar getJToolBar() {
        return this.barreOutils;
    }

    /**
     * Retourne une ImageIcon bas�� sur le nom d'image pass�� en param��tre
     * @param filePath le nom d'une image
     * @return une ImageIcon ou NULL si fichier inexistant
     */
    private static ImageIcon createImageIcon(String imageName) {
        ImageIcon image = null;
        String completeFilePath = "ressources/images/" + imageName + ".png";

        java.net.URL url = ClassLoader.getSystemClassLoader().getResource(completeFilePath);

        if (url == null) {
            System.err.println("Le fichier : " + completeFilePath + " n'existe pas !");
        } else {
            image = new ImageIcon(url);
        }

        return image;
    }

    private void createPanelControl() {
        
        Color bgColor = new Color(180, 180, 180);
        this.outerPanel.setBackground(bgColor);
        this.outerPanel.add(this.panelControl);
        this.panelControl.setBackground(bgColor);
        this.panelControl.setLayout(new GridBagLayout());
        this.panelControl.setPreferredSize(new Dimension(300, 450));
        
        Font h1 = new Font(Font.SERIF, Font.BOLD, 20);
        Font h2 = new Font(Font.SERIF, Font.ITALIC, 18);
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.ipadx = 20;
        c.ipady = 0;
        c.insets = new Insets(0, 10, 0, 0);
        this.label_ctrl_title1.setFont(h1);
        this.panelControl.add(this.label_ctrl_title1, c);
        c.gridy = 1;
        c.insets = new Insets(10, 20, 5, 0);
        this.label_ctrl_section1.setFont(h2);
        this.panelControl.add(this.label_ctrl_section1, c);
        c.gridy = 2;
        c.insets = new Insets(0, 25, 0, 0);
        this.chckbxDisplayLyrics.setBackground(bgColor);
        this.panelControl.add(this.chckbxDisplayLyrics, c);
        c.gridy = 3;
        this.chckbxDisplayChord.setBackground(bgColor);
        this.panelControl.add(this.chckbxDisplayChord, c);
        c.gridy = 4;
        c.insets = new Insets(10, 20, 0, 0);
        this.label_ctrl_section2.setFont(h2);
        this.panelControl.add(this.label_ctrl_section2, c);
        c.gridy = 5;
        c.insets = new Insets(0, 25, 0, 0);
        this.radioChordImg.setBackground(bgColor);
        this.panelControl.add(this.radioChordImg, c);
        c.gridy = 6;
        this.radioChordTxt.setBackground(bgColor);
        this.panelControl.add(this.radioChordTxt, c);
        c.gridy = 7;
        c.insets = new Insets(20, 10, 0, 0);
        this.label_ctrl_title2.setFont(h1);
        this.panelControl.add(this.label_ctrl_title2, c);
        c.gridy = 8;
        c.insets = new Insets(10, 20, 0, 0);
        this.label_ctrl_section3.setFont(h2);
        this.panelControl.add(this.label_ctrl_section3, c);
        c.gridy = 9;
        c.insets = new Insets(10, 25, 0, 0);
        this.panelControl.add(this.btnChangeFontLyrics, c);
        c.gridy = 10;
        this.panelControl.add(this.btnChangeFontChord, c);
        c.gridy = 11;
        c.insets = new Insets(10, 20, 0, 0);
        this.label_ctrl_section4.setFont(h2);
        this.panelControl.add(this.label_ctrl_section4, c);
        c.gridy = 12;
        c.insets = new Insets(10, 20, 0, 0);
        this.panelControl.add(this.cmbbxTransposition, c);
        c.gridy = 13;        
    }

    private void createPanelPartition() {
        this.panelPartition.setBackground(Color.GREEN);
    }

    private void createPanelFooter() {
        this.panelFooter.setBackground(new Color(99, 99, 99));
        this.panelFooter.setPreferredSize(new Dimension(0, 20));

    }

    private Font showWindowFont() {
        JFontChooser fontChooser = new JFontChooser();
        Font choosenFont = null;
        int result = fontChooser.showDialog(this.panelControl);
        if (result == JFontChooser.OK_OPTION) {
            choosenFont = fontChooser.getSelectedFont();
//            System.out.println("Selected Font : " + choosenFont);
        }
        return choosenFont;
    }
    
    /**
     * Permet d'activer ou desactiver le menu de selection de taille de police, la popup d'edition rapide
     * ainsi que le menu de selection du type d'affichage en fonction du booleen reçu
     * @param booleen le booleen
     */
    private void setIsEditable(boolean booleen) {
        this.menuFont.setEnabled(booleen);
        this.menuView.setEnabled(booleen);
        this.gestPartitions.setIsPopupMenuAndChordsChangeEnable(booleen);
        
    }

    private void setEvent() {

        //==================
        //       MENU
        //==================

        // Si new dans menu est cliqu��, on cr��e un nouveau document
        this.menuItemNew.addActionListener(new ActionListener() {

            @Override
            /**
             * Permet de créer un nouveau document
             */
            public void actionPerformed(ActionEvent ae) {
                gestPartitions.startNewDocument();
                GestionnaireFichiers.setPathEnregistrement("");
                gestPartitions.getGardienHTML().resetMemento();
            }
        });

        // Ajout de l'action ouvrir un fichier chordPro ou html au menu ouvrir      
        this.menuItemOpen.addActionListener(GestionnaireFichiers.openFileAction(this.gestPartitions));

        // Ajout de l'action sauvegarder au menu sauvegarder
        this.menuItemSave.addActionListener(GestionnaireFichiers.saveFileAction(this.gestPartitions));

        // Ajout de l'action sauvegarder sous au menu sauvegarder sous
        this.menuItemSaveAs.addActionListener(GestionnaireFichiers.save_asFileAction(this.gestPartitions));
        
        // Ajout de l'action imprimer au menu imprimer
        this.menuItemPrint.addActionListener(new ActionListener() {

            @Override
            /**
             * Action permettant d'imprimer le document
             */
            public void actionPerformed(ActionEvent e) {

                try {
                    gestPartitions.getJEditorPane().print();
                } catch (Exception a){}                

            }

        });
        
        // Ajout de l'action permettant de faire des exports PDF
        this.menuItemPdf.addActionListener(new ActionListener() {

            @Override
            /**
             * Action permettant l'export du fichier au format PDF
             */
            public void actionPerformed(ActionEvent ae) {
                GestionnaireFichiers.myFileChooser.setSelectedFile(new java.io.File(""));
                int resultat = GestionnaireFichiers.myFileChooser.showSaveDialog(null);
                if(resultat==0){
                    File selection = GestionnaireFichiers.myFileChooser.getSelectedFile();
                    String completeFileName = selection.getAbsolutePath() + ".pdf";
                    GestionnaireFichiers.exportPDF(gestPartitions, completeFileName);
                } 
            }
        });
        
        // Ajout de l'action permettant d'exporter le fichier en HTML (Attention sans les styles).
        this.menuItemHtml.addActionListener(new ActionListener() {

            @Override
            /**
             * Action permettant l'export du fichier au format HTML sans les styles
             */
            public void actionPerformed(ActionEvent ae) {
                GestionnaireFichiers.myFileChooser.setSelectedFile(new java.io.File(""));
                int resultat = GestionnaireFichiers.myFileChooser.showSaveDialog(null);
                if(resultat==0){
                    File selection = GestionnaireFichiers.myFileChooser.getSelectedFile();
                    String completeFileName = selection.getAbsolutePath() + ".html";
                    GestionnaireFichiers.exportHTML(gestPartitions, completeFileName);
                } 
            }
        });
        
        // Ajout de l'action permettant d'exporter le fichier en ChordPro.
        this.menuItemChordPro.addActionListener(new ActionListener() {

            @Override
            /**
             * Action permettant l'export du fichier en ChordPro
             */
            public void actionPerformed(ActionEvent ae) {
                GestionnaireFichiers.myFileChooser.setSelectedFile(new java.io.File(""));
                int resultat = GestionnaireFichiers.myFileChooser.showSaveDialog(null);
                if(resultat==0){
                    File selection = GestionnaireFichiers.myFileChooser.getSelectedFile();
                    String completeFileName = selection.getAbsolutePath() + ".txt";
                    GestionnaireFichiers.exportChordPro(gestPartitions, completeFileName);
                } 
            }
        });

        //Menu d'aide -> A propos ouvre une popup
        this.menuItemAbout.addActionListener(new ActionListener() {

            @Override
            /**
             * Permet d'afficher des informations générales sur le programme
             */
            public void actionPerformed(ActionEvent e) {
                //Bo����te du message d'information
                JOptionPane jop1 = new JOptionPane();
                jop1.showMessageDialog(null, "License GNU, 2012 - LERJEN Yann, ANGELOZ Gregory, LAGGER Yannick", GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_about"), JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //======================
        // Panneau de controle
        //======================

        //Bouton police d'accord
        this.btnChangeFontChord.addActionListener(new ActionListener() {

            @Override
            /**
             * Permet de changer le style de police utilisé pour afficher les accords
             * Les modification se font sur : 
             *      - le choix de la police
             *      - la décoration de la police (sousligné, gras, etc..)
             *      - la taille de la police
             */
            public void actionPerformed(ActionEvent e) {
                Font newFont = showWindowFont();
                if (newFont != null) {
                    String text = gestPartitions.getJEditorPane().getText();
                    HTMLEditorKit kit = gestPartitions.getHTMLEditorKit();
                    StyleSheet styleSheet = kit.getStyleSheet();
                    styleSheet.addRule(".chords {font-family:" + newFont.getFamily() + "; font-size:" + newFont.getSize() + ";}");
                    if (newFont.getStyle() == 1) {
                        styleSheet.addRule(".chords {font-weight:bold;}");
                    }
                    if (newFont.getStyle() == 2) {
                        styleSheet.addRule(".chords {font-style:italic;}");
                    }
                    if (newFont.getStyle() == 3) {
                        styleSheet.addRule(".chords {font-weight:bold;}");
                        styleSheet.addRule(".chords {font-style:italic;}");
                    }
                    Document oldDoc = gestPartitions.getJEditorPane().getDocument();
                    if (oldDoc != null) {
                        oldDoc.removeUndoableEditListener(gestPartitions.getUndoHandler());
                    }
                    Document doc = (HTMLDocument) kit.createDefaultDocument();
                    kit.setStyleSheet(styleSheet);
                    gestPartitions.getJEditorPane().setEditorKit(kit);
                    gestPartitions.getJEditorPane().setDocument(doc);
//                    gestPartitions.getJEditorPane().setContentType("text/plain;charset=UTF-8");
//                    gestPartitions.getJEditorPane().setContentType("text/html;charset=UTF-8");
                    gestPartitions.getJEditorPane().setText(text);
                    doc.addUndoableEditListener(gestPartitions.getUndoHandler());
                    //gestPartitions.getJEditorPane().setFont(newFont);
                }
            }
        });

        //Bouton police de texte
        this.btnChangeFontLyrics.addActionListener(new ActionListener() {

            @Override
            /**
             * Permet de changer le style de police utilisé pour afficher les paroles
             * Les modification se font sur : 
             *      - le choix de la police
             *      - la décoration de la police (sousligné, gras, etc..)
             *      - la taille de la police
             */
            public void actionPerformed(ActionEvent e) {
                Font newFont = showWindowFont();
                if (newFont != null) {
                    String text = gestPartitions.getJEditorPane().getText();
                    HTMLEditorKit kit = gestPartitions.getHTMLEditorKit();
                    StyleSheet styleSheet = kit.getStyleSheet();
                    styleSheet.addRule(".lyrics {font-family:" + newFont.getFamily() + "; font-size:" + newFont.getSize() + ";}");
                    if (newFont.getStyle() == 1) {
                        styleSheet.addRule(".lyrics {font-weight:bold;}");
                    }
                    if (newFont.getStyle() == 2) {
                        styleSheet.addRule(".lyrics {font-style:italic;}");
                    }
                    if (newFont.getStyle() == 3) {
                        styleSheet.addRule(".lyrics {font-weight:bold;}");
                        styleSheet.addRule(".lyrics {font-style:italic;}");
                    }
                    Document oldDoc = gestPartitions.getJEditorPane().getDocument();
                    if (oldDoc != null) {
                        oldDoc.removeUndoableEditListener(gestPartitions.getUndoHandler());
                    }
                    Document doc = (HTMLDocument) kit.createDefaultDocument();
                    kit.setStyleSheet(styleSheet);
                    gestPartitions.getJEditorPane().setEditorKit(kit);
                    gestPartitions.getJEditorPane().setDocument(doc);
//                    gestPartitions.getJEditorPane().setContentType("text/plain;charset=UTF-8");
//                    gestPartitions.getJEditorPane().setContentType("text/html;charset=UTF-8");
                    gestPartitions.getJEditorPane().setText(text);
                    doc.addUndoableEditListener(gestPartitions.getUndoHandler());
                    //gestPartitions.getJEditorPane().setFont(newFont);
                }
            }
        });

        /**
         * Permet de passer le programme à la langue française
         */
        this.menuLangageFrench.addActionListener(new ActionListener() {

            @Override
            /**
             * On change set la nouvelle langue en Fran�ais pour les boutons, menus, menusItem 
             * ainsi que pour la popup du JTextPane central
             */
            public void actionPerformed(ActionEvent ae) {
                GestionnaireLangues.setCurrentLanguage("Francais");
                loadLanguageMenuAndButton();
                gestPartitions.setLanguagePopup();
            }
        });
        
        /**
         * Permet de passer le programme à la langue anglaise
         */
        this.menuLangageEnglish.addActionListener(new ActionListener() {

            @Override
            /**
             * On change set la nouvelle langue en Anglais pour les boutons, menus, menusItem 
             * ainsi que pour la popup du JTextPane central
             */
            public void actionPerformed(ActionEvent ae) {
                GestionnaireLangues.setCurrentLanguage("English");
                loadLanguageMenuAndButton();
                gestPartitions.setLanguagePopup();
            }
        });

        /**
         * Permet de passer en mode edition html pour la modification des paroles
         */
        this.menuItemEditMode.addActionListener(new ActionListener() {

            @Override
            /**
             * Permet de passer en mode edition
             * Le mode edition donne accès à la partition au format plein text, c'est 
             * à dire que les balises html sont visibles
             * Les boutons d'actions copier / couper et coller sont également activés
             */
            public void actionPerformed(ActionEvent ae) {
                String text = gestPartitions.getJEditorPane().getText();
                gestPartitions.getJEditorPane().setContentType("text/plain");
                gestPartitions.getJEditorPane().setText(text);
                gestPartitions.getJEditorPane().setEditable(true);
                gestPartitions.getJEditorPane().getDocument().addUndoableEditListener(gestPartitions.getUndoHandler());
                gestPartitions.getJEditorPane().requestFocus();
                cut.setEnabled(true);
                cutItem.setEnabled(true);
                past.setEnabled(true);
                pasteItem.setEnabled(true);
                copy.setEnabled(true);
                copyItem.setEnabled(true);
            }
        });
        /**
         * Permet de passer en mode vue pour l'affichage du html classique
         */
        this.menuItemDisplayMode.addActionListener(new ActionListener() {

            @Override
            /**
             * Permet de passer en mode "vue"
             * Le mode "vue" affiche l'html en prenant en compte les styles et les balises
             * Seule l'edition des accords et l'edition rapide du texte via la pop-up du click-droit sont possibles via ce mode.
             * Les boutons d'actions copier / couper et coller sont également désactivés
             */
            public void actionPerformed(ActionEvent ae) {
                gestPartitions.getGardienHTML().resetMemento();
                gestPartitions.getCreateurHTML().setHTML(gestPartitions.getJEditorPane().getText());
                gestPartitions.getGardienHTML().addMemento(gestPartitions.getCreateurHTML().sauverDansMemento());
                String text = gestPartitions.getJEditorPane().getText();
                gestPartitions.getJEditorPane().setContentType("text/html");
                gestPartitions.getJEditorPane().setText(text);
                gestPartitions.getJEditorPane().setEditable(false);                
                gestPartitions.getJEditorPane().getDocument().addUndoableEditListener(gestPartitions.getUndoHandler());
                gestPartitions.getJEditorPane().requestFocus();
                cut.setEnabled(false);
                cutItem.setEnabled(false);
                past.setEnabled(false);
                pasteItem.setEnabled(false);
                copy.setEnabled(false);
                copyItem.setEnabled(false);
            }
        });
        
        this.chckbxDisplayChord.addActionListener(new ActionListener() {

            @Override
            /**
             * Permet d'afficher ou non les accords lorsqu'ils sont au format texte.
             * Si pas déjà fait, on crée un memento de l'état initial du HTML affiché, afin de récupérer cet état par la suite
             */
            public void actionPerformed(ActionEvent ae) {
                if (!chckbxDisplayChord.isSelected()) {                    
                    if (gestPartitions.getGardienHTML().getListSize() == 0) {
                        gestPartitions.getCreateurHTML().setHTML(gestPartitions.getJEditorPane().getText());
                        gestPartitions.getGardienHTML().addMemento(gestPartitions.getCreateurHTML().sauverDansMemento());                        
                    }                                            
                    
                    setIsEditable(false);
                    radioChordImg.setEnabled(false); //on ne peut plus passer en mode image si l'on cache les accords
                    
                    String fullText = gestPartitions.getJEditorPane().getText();
                    
                    Pattern pattern = Pattern.compile("<a .*?>(.*)?</a>", Pattern.MULTILINE);
                    Matcher matcher = pattern.matcher(fullText);
                    String lyrics = null;
                    while (matcher.find()) {
                        lyrics = matcher.group(0);
                    }
                    if (lyrics != null) {
                        pattern = Pattern.compile(lyrics);
                        String output = matcher.replaceAll("");
                        gestPartitions.getJEditorPane().setText(output);
                    }                     

                } else {                                        
                    
                    Memento previousMemento = gestPartitions.getGardienHTML().getMemento(gestPartitions.getGardienHTML().getListSize() - 1);
                    gestPartitions.getCreateurHTML().restaurerMemento(previousMemento);
                    String html = gestPartitions.getCreateurHTML().getHTML();
                    if (!chckbxDisplayLyrics.isSelected()) {                        
                        Pattern pattern = Pattern.compile("<span>(.*)?</span>", Pattern.MULTILINE);
                        Matcher matcher = pattern.matcher(html);
                        String lyrics = null;
                        while (matcher.find()) {
                            lyrics = matcher.group(0);
                        }
                        if (lyrics != null) {
                            pattern = Pattern.compile(lyrics);
                            String output = matcher.replaceAll("");
                            gestPartitions.getJEditorPane().setText(output);
                        }
                    } else {
                        setIsEditable(true);
                        radioChordImg.setEnabled(true);
                        gestPartitions.getJEditorPane().setText(html);
                    }
                }
            }
        });


        this.chckbxDisplayLyrics.addActionListener(new ActionListener() {

            @Override
            /**
             * Permet d'afficher ou non les paroles.
             * Si pas déjà fait, on crée un memento de l'état initial du HTML affiché, afin de récupérer cet état par la suite
             */
            public void actionPerformed(ActionEvent ae) {
                if (!chckbxDisplayLyrics.isSelected()) {
                   if (gestPartitions.getGardienHTML().getListSize() == 0) {
                        gestPartitions.getCreateurHTML().setHTML(gestPartitions.getJEditorPane().getText());
                        gestPartitions.getGardienHTML().addMemento(gestPartitions.getCreateurHTML().sauverDansMemento());
                    }
                   
                    setIsEditable(false);
                    radioChordImg.setEnabled(false); //on ne peut plus passer en mode image si l'on cache les paroles

                    String fullText = gestPartitions.getJEditorPane().getText();
                    //System.out.println(fullText);

                    Pattern pattern = Pattern.compile("<span>(.*)?</span>", Pattern.MULTILINE);
                    Matcher matcher = pattern.matcher(fullText);
                    String lyrics = null;
                    while (matcher.find()) {
                        lyrics = matcher.group(0);
                        //System.out.println(lyrics);                        
                    }
                    if (lyrics != null) {
                        pattern = Pattern.compile(lyrics);
                        String output = matcher.replaceAll("");
                        gestPartitions.getJEditorPane().setText(output);
                    }

                } else {                                        
                    
                    Memento previousMemento = gestPartitions.getGardienHTML().getMemento(gestPartitions.getGardienHTML().getListSize() - 1);
                    gestPartitions.getCreateurHTML().restaurerMemento(previousMemento);
                    String html = gestPartitions.getCreateurHTML().getHTML();
                    if (!chckbxDisplayChord.isSelected()) {                        
                        Pattern pattern = Pattern.compile("<a .*?>(.*)?</a>", Pattern.MULTILINE);
                        Matcher matcher = pattern.matcher(html);
                        String lyrics = null;
                        while (matcher.find()) {
                            lyrics = matcher.group(0);
                            //System.out.println(lyrics);                        
                        }
                        if (lyrics != null) {
                            pattern = Pattern.compile(lyrics);
                            String output = matcher.replaceAll("");
                            gestPartitions.getJEditorPane().setText(output);
                        }
                    } else {                        
                        setIsEditable(true);
                        radioChordImg.setEnabled(true);
                        gestPartitions.getJEditorPane().setText(html);
                    }
                }

            }
        });
                
        
        this.cmbbxTransposition.addActionListener(new ActionListener() {

            @Override
            /**
             * Permet de transposer toute la partition d'un ou plusieurs demi-tons vers le haut ou vers le bas
             */
            public void actionPerformed(ActionEvent e) {
                //TODO transpose toute la chanson
                String selectedValue = GestionnaireIhm.this.cmbbxTransposition.getSelectedItem().toString();
                if(selectedValue.length()>0 && selectedValue.length()!=1){
                    if("-".equals(selectedValue.substring(0,1)))
                        selectedValue=selectedValue.substring(0,2);
                    else
                        selectedValue=selectedValue.substring(0,2);
                    try{
                        int valeurTransposition=Integer.parseInt(selectedValue);
                        String song = gestPartitions.getJEditorPane().getText();
                        Pattern pattern = Pattern.compile("<a .*?>(.*)?</a>", Pattern.MULTILINE);
                        Matcher matcher = pattern.matcher(song);                

                        String output = null;
                        String accordTag = null;                
                        ArrayList<Accord> chordsList = new ArrayList<>();

                        while (matcher.find()) {                                                
                            Accord currentAccord = Accord.createChordByName(matcher.group(1));                                                                                  
                            chordsList.add(currentAccord);                                         
                        }

                        for(int i=0; i<chordsList.size(); i++) { 
                            /** on récupère le texte modifié à chaque itération **/
                            song = gestPartitions.getJEditorPane().getText();
                            Accord accord = Accord.createChordByName(chordsList.get(i).toString());
                            accord.transposeAccord(valeurTransposition);
                            pattern = Pattern.compile("<a\\s+href=\""+ chordsList.get(i).toString() +"\">"+chordsList.get(i).toString()+"</a>", Pattern.MULTILINE);
                            matcher = pattern.matcher(song);                        

                            /** pour chaque accord trouvé et ses correspondants, on remplace ses balises par une nouvelle balise image **/
                            while(matcher.find()) {                        
                                accordTag = matcher.group(0);                                                                        
                                output = matcher.replaceAll("<a href=\""+ accord +"\">"+ accord +"</a>");
                                gestPartitions.getJEditorPane().setText(output);
                                gestPartitions.getGardienHTML().resetMemento();
                                gestPartitions.getCreateurHTML().setHTML(gestPartitions.getJEditorPane().getText());
                                gestPartitions.getGardienHTML().addMemento(gestPartitions.getCreateurHTML().sauverDansMemento());
                            }
                    }
                    }catch(Exception ex){
                        System.out.println("erreure survenue, transposition impossible");
                    }
                }
                cmbbxTransposition.setSelectedItem("-");
            }
        });
                
          
        this.radioChordImg.addActionListener(new ActionListener() {

            @Override
            /**
             * Permet d'afficher les accords au format image
             * Lors de l'affichage au format image, les accords ou le texte ne peuvent plus être cachés
             */
            public void actionPerformed(ActionEvent ae) {
                if(radioChordImg.isSelected()) {
                    if(gestPartitions.getGardienHTML().getListSize()==0) {
                        gestPartitions.getCreateurHTML().setHTML(gestPartitions.getJEditorPane().getText());
                        gestPartitions.getGardienHTML().addMemento(gestPartitions.getCreateurHTML().sauverDansMemento());
                    }
                    setIsEditable(false);
                    chckbxDisplayChord.setEnabled(false);
                    chckbxDisplayLyrics.setEnabled(false);
                    String song = gestPartitions.getJEditorPane().getText();
                    Pattern pattern = Pattern.compile("<a .*?>(.*)?</a>", Pattern.MULTILINE);
                    Matcher matcher = pattern.matcher(song);
                    
                    
                    String output = null;
                    String accordTag = null;
                    String absolutPath = null;
                    ArrayList<Accord> chordsList = new ArrayList<>();
                    
                    while (matcher.find()) {                                                
                        Accord currentAccord = Accord.createChordByName(matcher.group(1));                                                                                                          
                        chordsList.add(currentAccord);                                         
                    }
                    
                    for(int i=0; i<chordsList.size(); i++) { 
                        /** on récupère le texte modifié à chaque itération **/
                        song = gestPartitions.getJEditorPane().getText();
                        pattern = Pattern.compile("<a\\s+href=\""+ chordsList.get(i).toString() +"\">"+chordsList.get(i).toString()+"</a>", Pattern.MULTILINE);
                        matcher = pattern.matcher(song);                        
                        
                        /** pour chaque accord trouvé et ses correspondants, on remplace ses balises par une nouvelle balise image **/
                        while(matcher.find()) {  
                            /** si on initialise avant, les images s'affichent avec un accord de Do .. **/
                            Frets manche = new Frets(chordsList.get(i));
                            accordTag = matcher.group(0);                            
                            String filePath = manche.exportImg(chordsList.get(i).toString());
                            absolutPath= new File(filePath).toURI().toString();
                            output = matcher.replaceAll("<img src=\""+ absolutPath +"\" />");
                            gestPartitions.getJEditorPane().setText(output);
                        }
                    }
                                                       
                }
            }
        });
        this.radioChordTxt.addActionListener(new ActionListener() {

            @Override
            /**
             * Permet d'afficher les accords au format texte
             * Lorsque les accords sont affichés au format texte, on peut choisir de n'afficher que les accords / que le texte
             */
            public void actionPerformed(ActionEvent ae) {      
                if(radioChordTxt.isSelected()) {                    
                    if(gestPartitions.getGardienHTML().getListSize()>0) {
                        Memento previousMemento = gestPartitions.getGardienHTML().getMemento(gestPartitions.getGardienHTML().getListSize()-1);
                        gestPartitions.getCreateurHTML().restaurerMemento(previousMemento);                    
                        gestPartitions.getJEditorPane().setText(gestPartitions.getCreateurHTML().getHTML());
                    }
                    chckbxDisplayChord.setEnabled(true);
                    chckbxDisplayLyrics.setEnabled(true);
                    setIsEditable(true);
                }
            }
        });
                
    }

    /**
     * Recharge le texte des Menu, MenuItem, Button etc lors d'un changement de langue
     */
    private void loadLanguageMenuAndButton() {
        this.menuFile.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_file"));
        this.menuItemNew.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_new"));
        this.menuItemOpen.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_open"));
        this.menuItemSave.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_save"));
        this.menuItemSaveAs.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_saveAs"));
        this.menuItemPrint.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_print"));
        this.menuItemExit.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_exit"));
        this.menuEdit.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_edit"));
        this.undoItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("undo"));
        this.redoItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("redo"));
        this.cutItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("cut"));
        this.copyItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("copy"));
        this.pasteItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("paste"));
        this.menuAlign.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_align"));

        this.leftAlignItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("align_left"));
        this.centerAlignItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("align_center"));
        this.rightAlignItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("align_right"));

        this.menuFont.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_font"));

        this.fontSizeMenu.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_fontsize"));

        this.menuView.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_view"));
        this.menuItemEditMode.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_editMode"));
        this.menuItemDisplayMode.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_viewMode"));


        this.menuSongBook.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_songBook"));
        this.menuItemNewSongBook.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_new_songBook"));
        this.menuItemOpenSongBook.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_open_songBook"));

        this.menuLangage.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_langage"));
        this.menuLangageFrench.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_langage_french"));

        this.menuHelp.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_help"));
        this.menuExport.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_export"));
        this.menuItemAbout.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_about"));
        this.btnChangeFontChord.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("label_control_font_chord"));
        this.btnChangeFontLyrics.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("label_control_font_lyrics"));                

        this.alignCenter.setToolTipText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("align_center"));
        this.leftAlign.setToolTipText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("align_left"));
        this.rightAlign.setToolTipText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("align_right"));
        this.alignCenter.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("align_center"));
        this.leftAlign.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("align_left"));
        this.rightAlign.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("align_right"));
        this.copy.setToolTipText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("copy"));
        this.copy.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("copy"));
        this.cut.setToolTipText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("cut"));
        this.cut.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("cut"));
        this.past.setToolTipText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("paste"));
        this.past.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("paste"));


        this.label_ctrl_title1.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("change_part_aspect"));
        this.label_ctrl_title2.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("modify_part"));
        this.label_ctrl_section1.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("content"));
        this.label_ctrl_section2.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("chord_type"));
        this.label_ctrl_section3.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("font"));
        this.label_ctrl_section4.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("transposition"));


        this.btnChangeFontChord.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("label_control_font_chord"));
        this.btnChangeFontLyrics.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("label_control_font_lyrics"));
        this.chckbxDisplayChord.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("label_show_chord"));
        this.chckbxDisplayLyrics.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("label_show_lyrics"));
        this.radioChordImg.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("label_image_chord"));
        this.radioChordTxt.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("label_text_chord"));

    }
}