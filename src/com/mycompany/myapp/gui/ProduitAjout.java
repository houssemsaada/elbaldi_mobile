/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanButton;
import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.SOUTH;
import static com.codename1.ui.CN.openGallery;
import static com.codename1.ui.CN1Constants.GALLERY_IMAGE;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextComponent;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.enities.Produits;
import com.mycompany.myapp.services.ProduitService;
import com.mycompany.myapp.utils.Statics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 *
 * @author yeekt
 */
public class ProduitAjout extends Form {
           private Resources theme;
                private EncodedImage enc;

    public ProduitAjout() throws IOException {
        
            
        setTitle("Ajouter Produit");
        setScrollableY(true);
        getToolbar().addCommandToSideMenu("Liste des offres",enc,
                e->{
           try {
               new GetOffres().show();
           } catch (IOException ex) {
               System.out.println(ex.getMessage());           }
        });
        getToolbar().addCommandToRightBar("back", null, ev->{
            new homeShared().show();
        });
     //   getToolbar().addCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, e->previous.showBack());
        //Button addDestination = new Button("Add Destination");

        //add(addDestination);
        //addDestination.addActionListener(e-> new AddDestinationForm(this).show()); 
        Container FormAjout =new Container(BoxLayout.yCenter());
        TextComponent refProd = new TextComponent().labelAndHint("refProd");
        FontImage.setMaterialIcon(refProd.getField().getHintLabel(), FontImage.MATERIAL_PERSON);
        
        TextComponent designation = new TextComponent().labelAndHint("designation");
        FontImage.setMaterialIcon(designation.getField().getHintLabel(), FontImage.MATERIAL_EMAIL);
        
        TextComponent qteStock = new TextComponent().labelAndHint("qteStock");
        FontImage.setMaterialIcon(qteStock.getField().getHintLabel(), FontImage.MATERIAL_LOCK);
        
        TextComponent prix = new TextComponent().labelAndHint("prix").constraint(TextArea.NUMERIC);
        FontImage.setMaterialIcon(prix.getField().getHintLabel(), FontImage.MATERIAL_LIBRARY_BOOKS);
        
        Button save = new Button("Save");
//        save.addActionListener(e -> {
//            ToastBar.showMessage("Save pressed...", FontImage.MATERIAL_INFO);
//            
//        });
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(((refProd.getText().length()==0)||(designation.getText().length()==0)||(prix.getText().length()==0)||(qteStock.getText().length()==0)))
                Dialog.show("Alert", "Remplir les champs", "OK","");
                 else
                {
                    Produits prod= new Produits(Integer.parseInt(qteStock.getText()), refProd.getText(), designation.getText() ,Float.parseFloat(prix.getText()));
                    
                        if( ProduitService.getInstance().addProd(prod))
                        {
                           Dialog.show("Success","Offre Ajouté","OK","");
                        try {
                            new GetOffres().show();
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                        }else
                            Dialog.show("ERROR", "Server error", "OK","");
                    
                    
                }
            }
        });

        
        Button avatar = new Button("");
        avatar.setUIID("InputAvatar");
        Image defaultAvatar = FontImage.createMaterial(FontImage.MATERIAL_CAMERA, "InputAvatarImage", 8);
        
        Image circleMaskImage =EncodedImage.create("/FileChooser.png");
        defaultAvatar = defaultAvatar.scaled(circleMaskImage.getWidth(), circleMaskImage.getHeight());
        defaultAvatar = ((FontImage)defaultAvatar).toEncodedImage();
        Object circleMask = circleMaskImage.createMask();
        defaultAvatar = defaultAvatar.applyMask(circleMask);
        avatar.setIcon(defaultAvatar);
        
        avatar.addActionListener(e -> {
            if(Dialog.show("Camera or Gallery", "Would you like to use the camera or the gallery for the picture?", "Camera", "Gallery")) {
                String pic = Capture.capturePhoto();
                if(pic != null) {
                    try {
                        Image img = Image.createImage(pic).fill(circleMaskImage.getWidth(), circleMaskImage.getHeight());
                        avatar.setIcon(img.applyMask(circleMask));
                    } catch(IOException err) {
                        ToastBar.showErrorMessage("An error occured while loading the image: " + err);
                        Log.e(err);
                    }
                }
            } else {
                openGallery(ee -> {
                    if(ee.getSource() != null) {
                        try {
                            Image img = Image.createImage((String)ee.getSource()).fill(circleMaskImage.getWidth(), circleMaskImage.getHeight());
                            avatar.setIcon(img.applyMask(circleMask));
                        } catch(IOException err) {
                            ToastBar.showErrorMessage("An error occured while loading the image: " + err);
                            Log.e(err);
                        }
                    }
                }, GALLERY_IMAGE);
            }
        });
        
        
        
        
        
        
        
        FormAjout.addAll(refProd,designation,qteStock,prix,avatar,save);
        add(FormAjout);
         }
            
    
    
    
// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        setLayout(new com.codename1.ui.layouts.LayeredLayout());
        setInlineStylesTheme(resourceObjectInstance);
                setInlineStylesTheme(resourceObjectInstance);
        setTitle("displaydestes");
        setName("displaydestes");
    }// </editor-fold>

//-- DON'T EDIT ABOVE THIS LINE!!!

    
}
    


