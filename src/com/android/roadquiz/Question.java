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

import com.android.roadquiz.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Question extends Activity {
	
	private int num = 0;
	private int correctAnswer = -1;
	private LinearLayout[] ll = new LinearLayout[4];
	private TextView[] answer = new TextView[4];
	private ImageView image1 = null;
	private TextView question = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        
        image1 = new ImageView(this);
		question = new TextView(this);
		for(int i = 0; i < 4; i++) {
			ll[i] = new LinearLayout(this);
			answer[i] = new TextView(this);
		}
		image1 = (ImageView)findViewById(R.id.image1);
		question = (TextView)findViewById(R.id.question);
		ll[0] = (LinearLayout)findViewById(R.id.answer1Layout);
		ll[1] = (LinearLayout)findViewById(R.id.answer2Layout);
		ll[2] = (LinearLayout)findViewById(R.id.answer3Layout);
		ll[3] = (LinearLayout)findViewById(R.id.answer4Layout);
		answer[0] = (TextView)findViewById(R.id.answer1);
		answer[1] = (TextView)findViewById(R.id.answer2);	
		answer[2] = (TextView)findViewById(R.id.answer3);
		answer[3] = (TextView)findViewById(R.id.answer4);		
        num = Choice.choice;
        reset();
        updateQuestion();
    }
    
    public void next(View v) {
    	num++;
    	reset();
    	updateQuestion();
    }
    
    public void updateQuestion() {
    	if(num >= 245) {
    		num = 0;
    	}
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
        	
        	if (num < nList.getLength()) {
         
        		Node nNode = nList.item(num);
         
        		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
         
        			Element eElement = (Element) nNode;
        			//Set Question
        			question.setText(eElement.getAttribute("number") + ". " + 
        							 eElement.getElementsByTagName("question").item(0).getTextContent());
        			
        			//Set image
        			String img = eElement.getElementsByTagName("image").item(0).getTextContent();
        			img = img.substring(img.lastIndexOf("/") + 1, img.indexOf(".")).replace('-', '_');
        			image1.setImageResource(getImageId(this, img));
        			
        			//Set answers
        			for(int i = 0; i < eElement.getElementsByTagName("answer").getLength(); i++) {
        				ll[i].setVisibility(View.VISIBLE);
        				answer[i].setText(eElement.getElementsByTagName("answer").item(i).getTextContent());
        			}
        			
        			//Find correct
        			for(int i = 0; i < eElement.getElementsByTagName("answer").getLength(); i++) {
        				if(eElement.getElementsByTagName("answer").item(i).getAttributes().getLength() > 0) {
        					correctAnswer = i;
        				}
        			}
        		}
        	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
    
    public void check(View v) {
    	int i = -1;
    	if(v.getId() == R.id.answer1Layout) {
    		i = 0;
    	} else if(v.getId() == R.id.answer2Layout) {
    		i = 1;
    	} else if(v.getId() == R.id.answer3Layout) {
    		i = 2;
    	} else if(v.getId() == R.id.answer4Layout) {
    		i = 3;
    	}
    	if(correctAnswer != i) {
    		ll[i].setBackgroundResource(R.drawable.btn_back_wrong);
    	}
    	disableClicks();
    	ll[correctAnswer].setBackgroundResource(R.drawable.btn_back_correct);
    }
    
    public void reset() {
		image1.setImageResource(android.R.color.transparent);
		question.setText("");
		for(int i = 0; i < 4; i++) {
			ll[i].setVisibility(View.GONE);
			ll[i].setClickable(true);
			ll[i].setBackgroundResource(R.drawable.btn_back);
			answer[i].setText("");
		}
		correctAnswer = -1;
    }
    
    public int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }
    
    public void disableClicks() {
    	for(int i = 0; i < 4; i++) {
			ll[i].setClickable(false);
		}
    }
}
