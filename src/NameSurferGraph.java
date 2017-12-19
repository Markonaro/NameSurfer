/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;

import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	
	/*
	 * Constants include those for the anticipated JContainer objects,
	 * height and width of the canvas, as well as how high above the
	 * graphed lines the names of each data set should be.
	 */
	public static final double yPad = 95;
	public static final double xPad = 16;
	
	public static double HEIGHT = NameSurferConstants.APPLICATION_HEIGHT-yPad;
	public static double WIDTH = NameSurferConstants.APPLICATION_WIDTH-xPad;
	
	public static final double NAME_PAD = 2;
	

	public NameSurferGraph() {
		addComponentListener(this);
		// You fill in the rest //
		clear();
	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		// You fill this in //
		graphOutline();
		namesEntered.clear();
		namesAndColors.clear();
	}
	
	/* This method is designed to add the various vertical lines and 
	 * decades associated with a particular NameSurferGraph
	 * */
	
	public void graphOutline(){
		removeAll();
		for(int i = 0; i < NameSurferConstants.NDECADES; i++){
			double xLine = (i*getWidth())/(NameSurferConstants.NDECADES);
			GLine vert = new GLine(xLine, 0, xLine, getHeight());
			add(vert);

			Integer forLab = NameSurferConstants.START_DECADE+i*10;
			GLabel lab = new GLabel(forLab.toString());
			lab.setLocation(xLine, getHeight());
			add(lab);
			
			if(i == NameSurferConstants.NDECADES-1){
				lowHoriz = new GLine(0, getHeight()-lab.getAscent(), getWidth(), getHeight()-lab.getAscent());
				add(lowHoriz);
				hiHoriz = new GLine(0, (lowHoriz.getY()-16)/NameSurferConstants.MAX_RANK+16,
						getWidth(), (lowHoriz.getY()-16)/NameSurferConstants.MAX_RANK+16);
				add(hiHoriz);
			}
		}
	}
	
	
	public void catalogEntry(NameSurferEntry entry){
	/*  Associates a constant color with each entry added to the canvas.
	 	Cycles through the various colors as names are entered.	*/
		if(namesAndColors.size() % 5 == 0){
			namesAndColors.put(entry, Color.BLUE);
			namesEntered.add(entry);
		} else if(namesAndColors.size() % 5 == 1){
			namesAndColors.put(entry, Color.RED);
			namesEntered.add(entry);
		} else if(namesAndColors.size() % 5 == 2){
			namesAndColors.put(entry, Color.GREEN);
			namesEntered.add(entry);
		} else if(namesAndColors.size() % 5 == 3){
			namesAndColors.put(entry, Color.ORANGE);
			namesEntered.add(entry);
		} else if(namesAndColors.size() % 5 == 4){
			namesAndColors.put(entry, Color.BLACK);
			namesEntered.add(entry);
		}
	}
	
	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display.
	 * Note that this method does not actually draw the graph, but
	 * simply stores the entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		
	// Obtains all of the decade ranks for the entry
		int[] entFreq = new int[NameSurferConstants.NDECADES];
		
		for(int i = 0; i < NameSurferConstants.NDECADES; i++){
			entFreq[i] = entry.getRank(i);
		// For display purposes, a rank of 0 should be at the very bottom rather than above 1 	
			if(entFreq[i] == 0){
				entFreq[i] = NameSurferConstants.MAX_RANK+1;
			}
		}
		
		for(int i = 0; i < NameSurferConstants.NDECADES; i++){
			double xLine = i*getWidth()/NameSurferConstants.NDECADES;
			GLine lines = new GLine(0.0, 0.0, 0.0, 0.0);
			
			GLabel names = new GLabel(entry.getName() + " " + entry.getRank(i));
			if(entry.getRank(i) == 0){
				names.setLabel(entry.getName() + " *");
			}
			
			double yLine = (lowHoriz.getY()-names.getHeight())*entFreq[i]/NameSurferConstants.MAX_RANK;
			
			if(entFreq[i] == 0){
				names.setLocation(xLine+NAME_PAD, names.getHeight()+yLine);
				lines.setLocation(xLine, lowHoriz.getY());
				lines.setEndPoint(xLine+(getWidth()/NameSurferConstants.NDECADES),
						(lowHoriz.getY()-names.getHeight())*entFreq[i+1]/NameSurferConstants.MAX_RANK+names.getHeight());
			} else if(i == NameSurferConstants.NDECADES-1){
				names.setLocation(xLine+NAME_PAD, names.getHeight()+yLine);
			} else {
				names.setLocation(xLine+NAME_PAD, names.getHeight()+yLine);
				lines.setLocation(xLine, names.getY());
				lines.setEndPoint(xLine+(getWidth()/NameSurferConstants.NDECADES), 
						(lowHoriz.getY()-names.getHeight())*entFreq[i+1]/NameSurferConstants.MAX_RANK+names.getHeight());
			}
			Color myColor = namesAndColors.get(entry);
			names.setColor(myColor);
			lines.setColor(myColor);
			add(names);
			add(lines);
		}
	}
	
	
	/**
	 * Updates the display image by deleting all the graphical objects
	 * from the canvas and then reassembling the display according to
	 * the list of entries. Your application must call update after
	 * calling either clear or addEntry; update is also called whenever
	 * the size of the canvas changes.
	 */
	public void update() {
		// You fill this in //
		   graphOutline();
		   
		   for(NameSurferEntry ent : namesEntered){
		   		addEntry(ent);
		   }

	}
	
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
	private GLine lowHoriz;
	private GLine hiHoriz;
	private ArrayList<NameSurferEntry> namesEntered = new ArrayList<NameSurferEntry>();
	private HashMap<NameSurferEntry,Color> namesAndColors = new HashMap<NameSurferEntry,Color>();
}
