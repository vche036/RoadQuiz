/*
 * Road Quiz
 * 
 * Developed by Victor Cheong
 * http://www.victorcheong.org
 * vche036@gmail.com
 * 
 */
package com.android.roadquiz;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class RoadQuiz extends ListActivity {
	 
	private String[] choices = new String[245];
	private int[] images = new int[245];
	
    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Read xml
		AssetManager assetManager = getAssets();

        //Read xml
        try {
        	InputStream input;
        	input = assetManager.open("quiz.xml");
        	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        	Document doc = dBuilder.parse(input);
        	input.close();		
        	doc.getDocumentElement().normalize();
         
        	NodeList nList = doc.getElementsByTagName("item");
        	
        	for(int num = 0; num < nList.getLength(); num++) {
         
        		Node nNode = nList.item(num);
         
        		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
         
        			Element eElement = (Element) nNode;
        			//Set Text
        			choices[num] = eElement.getElementsByTagName("question").item(0).getTextContent();
        			//Set Image
        			String img = eElement.getElementsByTagName("image").item(0).getTextContent();
        			img = img.substring(img.lastIndexOf("/") + 1, img.indexOf(".")).replace('-', '_');
        			images[num] = getImageId(this, img);
        		}
        	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
        setListAdapter(new MobileArrayAdapter(this, choices, images));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Choice.choice = position;
	    Intent StartGameIntent = new Intent(RoadQuiz.this,Question.class);
		startActivity(StartGameIntent);
	}
	
	public int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }
}