//gestisce l'animazione del logo

//import javafx.animation.*;
//import javafx.scene.image.*;
//import javafx.util.Duration;
        
//class Logo {
//    public int indice;
//    public ImageView logo;
//    public String[] pathLogo;
//    public Animation gif;
//    public double speed;
//    public double posX;
//    public double posY;
    
//    public Logo(ConfigXML conf) {
//        pathLogo = new String[6];
//        pathLogo[0] = conf.pathLogo1;
//        pathLogo[1] = conf.pathLogo2;
//        pathLogo[2] = conf.pathLogo3;
//        pathLogo[3] = conf.pathLogo4;
//        pathLogo[4] = conf.pathLogo5;
//        pathLogo[5] = conf.pathLogo6;
        
//        posX = 10;
//        posY = 30;
//    }
    
//    void Gif(){
//        gif = new Transition(){
//            {setCycleDuration(Duration.INDEFINITE);}
//            @Override
//            protected void interpolate(double frac){
//                Image img=new Image(pathLogo[indice]);
//                logo.setImage(img);
//                indice=(indice+1)%15;
//            }
//        };
//        gif.play();
//    }
    
//}
