/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.*;
import java.awt.event.*;


import java.io.File;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.undo.*;
import model.Accord;
import view.ChordSelectorGenerated;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.CreateurHTML;
import model.Frets;
import model.GardienHTML;
import model.PartocheTextPane;



/**
 * Gère la logique de du JTextPane qui affiche et permet la modifications des fichiers chordPro lus.
 * @author gregoryangeloz
 */
public class GestionnairePartitions { 
   /** pop-up d'édition rapide **/
   private static final JPopupMenu popupMenu = new JPopupMenu();   
   private boolean isPopupMenuAndChordsChangeEnable;
   private JScrollPane myScrollPane;
   private PartocheTextPane textPane;
   private HTMLDocument document;
   private File currentFile;
   private HTMLEditorKit kit;
   
   private JMenuItem boldMenuItem;
   private JMenuItem italicMenuItem;
   private JMenuItem underlineMenuItem;
   
   private JMenu subMenuColor;
   private JMenuItem redTextItem;
   private JMenuItem orangeTextItem;
   private JMenuItem yellowTextItem;
   private JMenuItem greenTextItem;
   private JMenuItem blueTextItem;
   private JMenuItem cyanTextItem;
   private JMenuItem magentaTextItem;
   private JMenuItem blackTextItem;
   
   
   /** Selectionneur d'accord pour la modification de ceux-ci **/
   ChordSelectorGenerated chordSelector;
   
   /** gères les actions redo et undo */
   private UndoManager undo;
   private UndoAction undoAction;
   private RedoAction redoAction;
         
   /** Ecouteur pour les opération d'éditions sur le document courant */
   private UndoableEditListener undoHandler;
   
   private Action cutAction;
   private Action copyAction;
   private Action pasteAction;
   
   private Action leftAlignAction;
   private Action centerAlignAction;
   private Action rightAlignAction;
    
    
   private Action boldAction;
   private Action underlineAction;
   private Action italicAction;
   
   private GardienHTML gardien;
   private CreateurHTML createur;
   
   /**
    * Constructeur 
    * Initialise une instance unique du GestionnaireLangues.rendGestionnaireLangues() (pattern Singleton)
    * Lance la méthode create() qui se charge de créer le JScrollPane contenant le JEditorPane
    */
   public GestionnairePartitions() {                     
       this.create();
       setEvent();
   }
   /**
    * Se charge de créer les différents JElements nécessaires au bon fonctionnement du JTextPane
    * - On construit un objet HTMLEditorKit qui va permettre l'édition de nos fichiers textes
    * - On construit un objet JTextPane qui va contenir HTMLEditorKit
    * - On construit un objet JSscrollPane qui contiendra le JTextPane pour que notre fenêtre supporte le scroll vertical / horizontal 
    * - On construit la pop-up d'édition rapide accessible via le click-droit
    */
   private void create() {     
      this.isPopupMenuAndChordsChangeEnable = true;
      
      kit = new HTMLEditorKit();
      //document = (HTMLDocument)kit.createDefaultDocument();
      //document.addUndoableEditListener(undoHandler);                  
                          
      textPane = new PartocheTextPane();             
      
      myScrollPane = new JScrollPane(textPane);    
      myScrollPane.setPreferredSize(new Dimension(500,500));      
      
      textPane.setEditorKit(kit);                  
      //textPane.setContentType("text/plain;charset=UTF-8");                      
            
      textPane.setEditable(false); 
      
      this.cutAction = new DefaultEditorKit.CutAction();   
      this.copyAction = new DefaultEditorKit.CopyAction();
      this.pasteAction = new DefaultEditorKit.PasteAction();
      
      this.undo = new UndoManager();
      this.undoAction = new UndoAction();
      this.redoAction = new RedoAction();
      this.undoHandler = new UndoHandler();
      
      this.leftAlignAction = new StyledEditorKit.AlignmentAction("Aligner à gauche",StyleConstants.ALIGN_LEFT);
      this.centerAlignAction = new StyledEditorKit.AlignmentAction("Centrer",StyleConstants.ALIGN_CENTER);
      this.rightAlignAction = new StyledEditorKit.AlignmentAction ("Aligner à droite",StyleConstants.ALIGN_RIGHT);


      this.boldAction = new StyledEditorKit.BoldAction();
      this.underlineAction = new StyledEditorKit.UnderlineAction();
      this.italicAction = new StyledEditorKit.ItalicAction();

      
      /** pour la selection des accords **/
      this.chordSelector =  new ChordSelectorGenerated(textPane);                        
              
      this.boldMenuItem = new JMenuItem(boldAction);
      this.boldMenuItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("bold"));
      this.italicMenuItem = new JMenuItem(italicAction);
      this.italicMenuItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("italic"));
      this.underlineMenuItem = new JMenuItem(underlineAction);
      this.underlineMenuItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("underline"));
      
      this.subMenuColor = new JMenu(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("colors"));
      this.redTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("red"),Color.red));
      this.orangeTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("orange"),Color.orange));
      this.yellowTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("yellow"),Color.yellow));
      this.greenTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("green"),Color.green));
      this.blueTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("blue"),Color.blue));
      this.cyanTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("cyan"),Color.cyan));
      this.magentaTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("magenta"),Color.magenta));
      this.blackTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("black"),Color.black));
      
      subMenuColor.add(redTextItem);
      subMenuColor.add(orangeTextItem);
      subMenuColor.add(yellowTextItem);
      subMenuColor.add(greenTextItem);
      subMenuColor.add(blueTextItem);
      subMenuColor.add(cyanTextItem);
      subMenuColor.add(magentaTextItem);
      subMenuColor.add(blackTextItem);     
            
      popupMenu.add(boldMenuItem);
      popupMenu.add(italicMenuItem);
      popupMenu.add(underlineMenuItem);
      popupMenu.add(subMenuColor);
      
      this.gardien = new GardienHTML();
      this.createur = new CreateurHTML();
            
      ajouteEvenementsClavier();      
   }
   
   /**
     * Permet la gestion du CTRL-Z ou Pomme-Z pour le composant spécifié
     *
     * @param composantSwing Le composant sur lequel on souhaite appliquer le CTRL-Z
     */
    private void ajouteUndoClavier(JComponent composantSwing) {
        final String NOM_ACTION = "Undo";

        Action actionUndo = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                        undo.undo();                        
                    } catch (CannotUndoException cue) {
                        Toolkit.getDefaultToolkit().beep();
                      }
            }
        };

        composantSwing.getActionMap().put(NOM_ACTION, actionUndo);

        InputMap[] inputMaps = new InputMap[]{
            composantSwing.getInputMap(JComponent.WHEN_FOCUSED),
            composantSwing.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT),
            composantSwing.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)};
        for (InputMap map : inputMaps) {
            map.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), NOM_ACTION);
        }
    }
    /**
     * Permet la gestion du CTRL-Y ou Pomme-Y pour le composant spécifié
     *
     * @param composantSwing Le composant sur lequel on souhaite appliquer le CTRL-Z     
     */
    private void ajouteRedoClavier(JComponent composantSwing) {
        final String NOM_ACTION = "Redo";

        Action actionRedo = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                        undo.redo();                        
                    } catch (CannotRedoException cue) {
                        Toolkit.getDefaultToolkit().beep();
                      }
            }
        };

        composantSwing.getActionMap().put(NOM_ACTION, actionRedo);

        InputMap[] inputMaps = new InputMap[]{
            composantSwing.getInputMap(JComponent.WHEN_FOCUSED),
            composantSwing.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT),
            composantSwing.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)};
        for (InputMap map : inputMaps) {
            map.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), NOM_ACTION);
        }
    }
    
    /**
     * Ajoute la gestion du CTRL-S ou Pomme-S pour le composant spécifié
     * @param composantSwing  Le composant sur lequel on souhaite appliquer le CTRL-S
     */
    private void ajoute_CTRL_S_Clavier(JComponent composantSwing) {
        final String NOM_ACTION = "Save";

        Action actionSave = GestionnaireFichiers.saveFileAction(GestionnairePartitions.this);

        composantSwing.getActionMap().put(NOM_ACTION, actionSave);

        InputMap[] inputMaps = new InputMap[]{
            composantSwing.getInputMap(JComponent.WHEN_FOCUSED),
            composantSwing.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT),
            composantSwing.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)};
        for (InputMap map : inputMaps) {
            map.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), NOM_ACTION);
        }
    }
    /**
     * Ajoute la gestion du CTRL-N ou Pomme-N pour le composant spécifié
     * @param composantSwing Le composant sur lequel on souhaite appliquer le CTRL-N
     */
    private void ajoute_CTRL_N_Clavier(JComponent composantSwing) {
        final String NOM_ACTION = "New";

        Action actionNew = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                      startNewDocument();
                    } catch (CannotRedoException cue) {
                        Toolkit.getDefaultToolkit().beep();
                      }
            }
        };

        composantSwing.getActionMap().put(NOM_ACTION, actionNew);

        InputMap[] inputMaps = new InputMap[]{
            composantSwing.getInputMap(JComponent.WHEN_FOCUSED),
            composantSwing.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT),
            composantSwing.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)};
        for (InputMap map : inputMaps) {
            map.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), NOM_ACTION);
        }
    }
    /**
     * Ajoute la gestion du CTRL-O ou Pomme-O pour le composant spécifié
     * @param composantSwing Le composant sur lequel on souhaite appliquer le CTRL-O
     */
    private void ajoute_CTRL_O_Clavier(JComponent composantSwing) {
        final String NOM_ACTION = "Open";

        Action actionNew = GestionnaireFichiers.openFileAction(GestionnairePartitions.this);

        composantSwing.getActionMap().put(NOM_ACTION, actionNew);

        InputMap[] inputMaps = new InputMap[]{
            composantSwing.getInputMap(JComponent.WHEN_FOCUSED),
            composantSwing.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT),
            composantSwing.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)};
        for (InputMap map : inputMaps) {
            map.put(KeyStroke.getKeyStroke(KeyEvent.VK_O, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), NOM_ACTION);
        }
    }
    
    
    /**
     * Permet la modification de la langue sur les éléments de la pop-up d'édition rapide
     */
    public void setLanguagePopup() {        
        this.boldMenuItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("bold"));        
        this.italicMenuItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("italic"));        
        this.underlineMenuItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("underline"));      
        this.subMenuColor.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("colors"));
        this.redTextItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("red"));
        this.orangeTextItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("orange"));
        this.yellowTextItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("yellow"));
        this.greenTextItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("green"));
        this.blueTextItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("blue"));
        this.cyanTextItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("cyan"));
        this.magentaTextItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("magenta"));
        this.blackTextItem.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("black"));
    }
    
   /**
    * Permet la création d'un nouveau document et remet à zéro
    * le memento pour les undo / redo
    */
   public void startNewDocument(){       
        GestionnaireFichiers.setPathEnregistrement(""); //on set le pathEnregistrement à null 
        Document oldDoc = textPane.getDocument();
        if(oldDoc != null)
                oldDoc.removeUndoableEditListener(undoHandler);
        kit = this.getHTMLEditorKit();        
        document = (HTMLDocument)kit.createDefaultDocument();        
        document.addUndoableEditListener(undoHandler);          
        StyleSheet styleSheet = kit.getStyleSheet();       
        styleSheet.addRule("{font-size:12px; font-family:Arial}");
        styleSheet.addRule("h1 {font-weight: bold; font-size:16px; font-family:Arial}");
        styleSheet.addRule("a {text-decoration:none;}");
        /** si on a changé la police des lyrics ou des chords, on revient à celle de base **/
        styleSheet.removeStyle(".lyrics");
        styleSheet.removeStyle(".chords");        
        /** ajout d'un hyperlinkListener pour le survol d'un accord **/
        textPane.addHyperlinkListener(new HTMLListener()); 		
        /** POUR TEST **/              
        //textPane.setOriginalHTML(HTML);
        textPane.setDocument(document);	                   
        
        //this.refresh();

        currentFile = null;				
        resetUndoManager();                
	}
   
   /**
    * Pour le dév.
    * Rafraichit la page pour éviter des bugs d'affichage des balises html
    */
   public void refresh() {
       textPane.setContentType("text/plain");
       textPane.setContentType("text/html");       
   }
   /**
    * Reset le memento des fonctions undo et redo
    */
   protected void resetUndoManager() {
		undo.discardAllEdits();
		undoAction.update();
		redoAction.update();
	}
   
   /**
    * Permet de récupérer le JEditorPane
    * @return le JEditorPane
    */
   public JEditorPane getJEditorPane() {
       return this.textPane;
   }
   
   
   /**
    * Permet de récupérer le JScrollPane
    * @return le JScrollPane
    */
   public JScrollPane getJScrollPane() {
       return this.myScrollPane;
   }
   /**
    * Permet de récupérer l'action Undo
    * @return l'action undo
    */
   public UndoAction getUndoAction() {
       return this.undoAction;
   }
   /**
    * Permet de récupérer l'action Redo
    * @return l'action redo
    */
   public RedoAction getRedoAction() {
       return this.redoAction;
   }
   /**
    * Permet de récupérer l'action Cut
    * @return l'action cut
    */
   public Action getCutAction() {
       return this.cutAction;
   }
   /**
    * Permet de récupérer l'action Copy
    * @return l'action copy
    */
   public Action getCopyAction() {
       return this.copyAction;
   }
   /**
    * Permet de récupérer l'action Paste
    * @return l'action paste
    */
   public Action getPasteAction() {
       return this.pasteAction;
   }
   /**
    * Permet de récupérer l'action d'alignement du texte sur la gauche
    * @return l'action d'alignement texte à gauche
    */
   public Action getLeftAlignAction() {
       return this.leftAlignAction;
   }
   /**
    * Permet de récupérer l'action d'alignement du texte sur le centre
    * @return l'action d'alignement texte au centre
    */
   public Action getCenterAlignAction() {
       return this.centerAlignAction;
   }
   /**
    * Permet de récupérer l'action d'alignement du texte sur la droite
    * @return l'action d'alignement texte à droite
    */
   public Action getRighAlignAction() {
       return this.rightAlignAction;
   }
   /**
    * Permet de retourner le gestionnaire des actions undo (ecouteur pour les évenement Undo)
    * @return l'écouteur des évenements Undo
    */
   public UndoableEditListener getUndoHandler() {
       return this.undoHandler;
   }
   /**
    * Permet de retourner l'objet UndoManager 
    * @return undo l'UndoManager
    */   
   public UndoManager getUndoManager() {
       return this.undo;
   }
   /**
    * Permet de retourner l'EditorKit du JTextPane
    * @return le JEditorKit du JTextPane
    */
   public HTMLEditorKit getHTMLEditorKit() {
       return this.kit;
   }
   
   /**
    * Permet de retourner le document du JTextPane
    * @return le Document du JTextPane
    */
   public HTMLDocument getPartocheDocument() {
       return this.document;
   }
   
   /**
    * Permet de retourner le GardienHTML pour sauver des Memento du html
    * @return le gardien HTML
    */
   public GardienHTML getGardienHTML() {
       return this.gardien;
   }
   
   /**
    * Permet de retourner le CreateurHTML pour créer des Memento du html
    * @return le createur HTML
    */
   public CreateurHTML getCreateurHTML() {
       return this.createur;
   }
   
   /**
    * Permet d'activer ou de desactiver la popup et l'edition des accords en fonction du booleen reçu
    * @param booleen le booleen
    */
   public void setIsPopupMenuAndChordsChangeEnable(boolean booleen) {
       this.isPopupMenuAndChordsChangeEnable = booleen;
   }
   
   
   /**
    * Permet d'ouvrir la fenêtre de selection des accords qui offre la possibilité
    * de modifier l'accord passé en paramètre
    * @param accordAChanger l'accord à modifier
    * @return Accord l'accord modifié
    */
   public Accord changeAccord(Accord accordAChanger){
        return this.chordSelector.showDialog(this.getJEditorPane().getParent(), accordAChanger);
    }
   
   /**
    * Permet de créer un HTMLListener pour écouter les liens hypertextes
    * @return HTMLListener un HTMLListener
    */
   public HTMLListener getHTMLListener() {
       return new HTMLListener();
   }

   /**
    * Ajoute un MouseListener au JTextPane pour la gestion du click-droit d'édition rapide
    *
    * implémentation d'un MouseAdapter pour l'affichage de la popup lors d'un click-droit
    *  -> DESIGN-PATTERN ADAPTER       
    */
    private void setEvent() {       
      textPane.addMouseListener(new MouseAdapter() {
          public void mouseClicked(MouseEvent e) {
            /** si click-droit, on affiche la pop-up **/
            if(e.getButton() == MouseEvent.BUTTON3 && "text/html".equals(textPane.getContentType()) && isPopupMenuAndChordsChangeEnable) {
                popupMenu.show(e.getComponent(), e.getX(), e.getY());                
            }
        }          
      });            
    }

    /**
     * Implémente tous les évenements clavier
     *  - CTRL-Z
     *  - CTRL-Y
     *  - CTRL-S
     *  - CTRL-N
     *  - CTRL-O
     */
    private void ajouteEvenementsClavier() {
        this.ajouteRedoClavier(textPane);
        this.ajouteUndoClavier(textPane);   
        this.ajoute_CTRL_S_Clavier(textPane);
        this.ajoute_CTRL_N_Clavier(textPane);
        this.ajoute_CTRL_O_Clavier(textPane);
    }
  
   /**
    * Ecouteur d'edition
    */
    private class UndoHandler implements UndoableEditListener {

		/**
                 * Lorsque le document à été édité, l'état précédent est ajouté
                 * à undo 		 
		 */
		public void undoableEditHappened(UndoableEditEvent e) {
			undo.addEdit(e.getEdit());
			undoAction.update();
			redoAction.update();
		}
    }
    /**
     * Classe interne qui permet d'annuler la dernière action effectuée (undo)
     */
    private class UndoAction extends AbstractAction {
        /**
         * Constructeur
         */
        public UndoAction() {
                super("Undo");
                setEnabled(false);
        }

        /**
         * Gère l'action Undo qui annule la dernière modification effectuée
         * @param e l'event
         */
        public void actionPerformed(ActionEvent e) {
                try {
                        undo.undo();
                } catch (CannotUndoException ex) {
                        System.out.println("Impossible d'annuler: " + ex);
                        ex.printStackTrace();
                }
                update();
                redoAction.update();
        }
        /**
         * Permet d'activer le MenuItem Undo sitôt qu'un ajout à été fait
         */
        protected void update() {
                if(undo.canUndo()) {
                        setEnabled(true);
                        putValue(Action.NAME, undo.getUndoPresentationName());
                }else {
                        setEnabled(false);
                        putValue(Action.NAME, "Annuler");
                }
        }
}
    
    /**
     * Classe interne qui permet de rétablir la dernière action annulée (redo)
     */
    private class RedoAction extends AbstractAction {
        /**
         * Constructeur
         */
        public RedoAction() {
                super("Redo");
                setEnabled(false);
        }
        /**
         * Gère l'action Redo qui annule la dernière annulation effectuée
         * @param e l'event
         */
        public void actionPerformed(ActionEvent e) {
                try {
                        undo.redo();
                } catch (CannotRedoException ex) {
                        System.err.println("Impossible de rétablir: " + ex);
                        ex.printStackTrace();
                }
                update();
                undoAction.update();
        }
        /**
         * Permet d'activer le MenuItem Redo sitôt qu'une annulation à été faite
         */
        protected void update() {
                if(undo.canRedo()) {
                        setEnabled(true);
                        putValue(Action.NAME, undo.getRedoPresentationName());
                }else {
                        setEnabled(false);
                        putValue(Action.NAME, "Rétablir");
                }
        }
}
    
    /**
     * Ecouteur de liens html (<a href='' />) dans lesquels sont contenus les accords.
     * Au survol d'un de ces derniers, l'écouteur permet l'affichage de l'image de l'accord au format Guitar Chord.
     */
    private class HTMLListener implements HyperlinkListener {
        
        /**
         * Permet un certain nombre d'actions lorsque la souris survol un lien html (<a>...</a>)
         *  - Si la souris survol un lien (accord), une popup avec l'image représentative de cet accord s'affiche
         *  - Si l'on effectue un double-click sur l'un de ces accords, la fenêtre permettant la modification des accords s'affiche
         */
        @Override
        public void hyperlinkUpdate(HyperlinkEvent he) {
            if (he.getEventType() == HyperlinkEvent.EventType.ENTERED) {
                
                Accord selectedAccord = Accord.createChordByName(he.getDescription());
                
                Frets manche = new Frets(selectedAccord);                
                String filePath = manche.exportImg();                                                                              
                
                textPane.setToolTipText("");    //obligatoire sinon la pop-up ne s'affiche pas
                                                                               
            }  
            /** Quand le curseur sort de l'accord, on supprime la ToolTip **/
            if (he.getEventType() == HyperlinkEvent.EventType.EXITED) {
                textPane.setToolTipText(null);
                
            }
            /** si click et que le booleen de changement d'accords est à true, on peut modifier l'accord selectionné
             *  NB : cette action changera tous les accords de la meme famille, par exemple une modification d'un accord
             *       de DO entrainera la modification de tous les accords de DO de la partition
             */
            if (he.getEventType() == HyperlinkEvent.EventType.ACTIVATED && isPopupMenuAndChordsChangeEnable) {                 
                
                Accord selectedAccord = Accord.createChordByName(he.getDescription());              
                
                Accord newAccord = GestionnairePartitions.this.changeAccord(selectedAccord);
                if(newAccord!=null) {
                    String wholeText = textPane.getText();
                    Pattern pattern = Pattern.compile(" <a\\s+href=\""+he.getDescription()+"\">"+he.getDescription()+"</a>", Pattern.MULTILINE);                
                    Matcher matcher = pattern.matcher(wholeText);
                    /** on parcourt tous les résultats positifs
                     *  et on les stocke dans un tableau de String **/
                    String oldAccord = null;
                    while (matcher.find())
                    {                  
                      oldAccord = matcher.group(0);                        
                    }
                    if(oldAccord!=null) {
                        pattern = Pattern.compile(oldAccord);
                        String newAccordHTML = " <a href=\""+newAccord.toString()+"\">"+newAccord.toString()+"</a>";
                        String output = matcher.replaceAll(newAccordHTML);
                        textPane.setText(output); 
                        gardien.resetMemento();
                        createur.setHTML(textPane.getText());
                        gardien.addMemento(createur.sauverDansMemento());
                        undo.undo(); // sinon, on doit faire 4 ctrl-z pour retrouver l'ancien accord...
                        undo.undo();                           
                    }
                }
                else {
                    System.out.println("Modification annulée");
                }
            }
        }
                
    }
 
}
