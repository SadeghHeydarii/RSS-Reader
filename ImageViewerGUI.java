import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;

public class ImageViewerGUI extends JFrame implements ActionListener{
    JButton selectFileButton;
    JButton showImageButton;
    JButton resizeButton;
    JButton grayscaleButton;
    JButton brightnessButton;
    JButton closeButton;
    JButton showResizeButton;
    JButton showBrightnessButton;
    JButton backButton;
    JButton applySize;
    JTextField widthTextField;
    JTextField heightTextField;
    JTextField brightnessTextField;
    String filePath = "C:";
    File file;
    JFileChooser fileChooser = new JFileChooser(filePath);
    int h = 900;
    int w = 1200;
    float brightenFactor = 1;

    ImageViewerGUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Image Viewer");
        this.setSize(700, 300);
        this.setVisible(true);
        this.setResizable(true);

        mainPanel();
    }

    public void mainPanel(){
        // Create main panel for adding to Frame
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        // Create Grid panel for adding buttons to it, then add it all to main panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 2));

        // set Grid panel bounds and set panel buttons words and ActionListener
        buttonsPanel.setBounds(200,50,300,150);
        selectFileButton = new JButton("Choose Image");
        selectFileButton.addActionListener(this);
        showImageButton = new JButton("Show Image");
        showImageButton.addActionListener(this);
        brightnessButton = new JButton("Brightness");
        brightnessButton.addActionListener(this);
        grayscaleButton = new JButton("Gray scale");
        grayscaleButton.addActionListener(this);
        resizeButton = new JButton("Resize");
        resizeButton.addActionListener(this);
        closeButton = new JButton("Exit");
        closeButton.addActionListener(this);

        // Adding all buttons to Grid panel
        buttonsPanel.add(this.selectFileButton);
        buttonsPanel.add(this.showImageButton);
        buttonsPanel.add(this.brightnessButton);
        buttonsPanel.add(this.grayscaleButton);
        buttonsPanel.add(this.resizeButton);
        buttonsPanel.add(this.closeButton);

        // add Grid panel that contains 6 buttons to main panel
        mainPanel.add(buttonsPanel);

        // add main panel to our frame
        this.add(mainPanel);
    }

    public void resizePanel(){
        JPanel resizePanel = new JPanel();
        resizePanel.setLayout(null);

        // create and set resizePanel text fields
        widthTextField = new JTextField();
        heightTextField = new JTextField();
        widthTextField.setBounds(300,100,150,25);
        heightTextField.setBounds(300,125,150,25);
        resizePanel.add(widthTextField);
        resizePanel.add(heightTextField);

        // create and set resizePanel text labels
        JLabel widthLabel = new JLabel("width:");
        JLabel heightLabel = new JLabel("height:");
        widthLabel.setBounds(250,100,100,20);
        heightLabel.setBounds(250,125,100,20);
        resizePanel.add(widthLabel);
        resizePanel.add(heightLabel);

        // create and set resizePanel buttons
        showResizeButton = new JButton("Show Result");
        backButton = new JButton("Back");
        showResizeButton.setBounds(100,200,120,40);
        backButton.setBounds(500,200,120,40);
        showResizeButton.addActionListener(this);
        backButton.addActionListener(this);
        resizePanel.add(showResizeButton);
        resizePanel.add(backButton);

        // create and set resizePanel apply size button
        applySize = new JButton("Apply this scale for other operations");
        applySize.setBounds(225,160,250,25);
        applySize.addActionListener(this);
        resizePanel.add(applySize);

        this.add(resizePanel);
    }
    public void brightnessPanel(){
        JPanel resizePanel = new JPanel();
        resizePanel.setLayout(null);

        // create and set brightnessPanel text fields
        brightnessTextField = new JTextField();
        brightnessTextField.setBounds(300,100,150,25);
        resizePanel.add(brightnessTextField);

        // create and set brightnessPanel text labels
        JLabel brightnessLabel = new JLabel("brightness:");
        brightnessLabel.setBounds(225,100,100,20);
        resizePanel.add(brightnessLabel);

        // create and set brightnessPanel buttons
        showBrightnessButton = new JButton("Show Result");
        backButton = new JButton("Back");
        showBrightnessButton.setBounds(100,200,120,40);
        backButton.setBounds(500,200,120,40);
        showBrightnessButton.addActionListener(this);
        backButton.addActionListener(this);
        resizePanel.add(showBrightnessButton);
        resizePanel.add(backButton);

        this.add(resizePanel);
    }

    public void chooseFileImage(){

        // create file chooser panel
        fileChooser.setAcceptAllFileFilterUsed(false);

        int option = fileChooser.showOpenDialog(ImageViewerGUI.this);
        // exception handling for choosing file
        try {
            if (option == JFileChooser.APPROVE_OPTION) {
                File givenFile = fileChooser.getSelectedFile();
                // check the format of file to be image file
                String format = givenFile.getName().substring(givenFile.getName().lastIndexOf('.')+1);
                if(format.equals("jpg") || format.equals("png") || format.equals("jpeg")){
                    file = fileChooser.getSelectedFile();
                    ImageIcon scaledInstance = new ImageIcon(String.valueOf(file));
                    w = scaledInstance.getIconWidth();
                    h = scaledInstance.getIconHeight();
                }
                else {
                    file = null;
                    JLabel jLabel = new JLabel("Wrong file type!");
                    jLabel.setBounds(300,180,100,100);
                    this.add(jLabel);
                }
            }
        }
        catch (Exception e){
            System.out.println("No image selected!");
        }
        this.mainPanel();

    }
    public void showOriginalImage(){
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        JLabel tempLabel = new JLabel();

        // show image in panel
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(String.valueOf(file)).getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
        tempLabel.setIcon(imageIcon);

        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
        tempPanel.add(tempLabel);
    }

    public void grayScaleImage(){
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        JLabel tempLabel = new JLabel();

        // convert image to grayscale image and show it

        Image colorImage = new ImageIcon(String.valueOf(file)).getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT);
        ImageFilter filter = new GrayFilter(true, 50);
        ImageProducer producer = new FilteredImageSource(colorImage.getSource(), filter);
        Image image = Toolkit.getDefaultToolkit().createImage(producer);
        ImageIcon imageIcon = new ImageIcon(image);
        tempLabel.setIcon(imageIcon);

        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
        tempPanel.add(tempLabel);
    }
    public void showResizeImage(int w, int h){
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        JLabel tempLabel = new JLabel();

        // show image with entered size
        try {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(String.valueOf(file)).getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
            tempLabel.setIcon(imageIcon);
        }
        catch (Exception e){
            System.out.println("Invalid scales!");
        }

        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
        tempPanel.add(tempLabel);
    }
    public void showBrightnessImage(float f){
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        JLabel tempLabel = new JLabel();

        // change brightness of image and show it
        try {
            BufferedImage image = ImageIO.read(file);
            RescaleOp op = new RescaleOp(f, 0, null);
            Image brightImage = op.filter(image, image);
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(brightImage).getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
            tempLabel.setIcon(imageIcon);
        }
        catch (Exception e){
            System.out.println("Invalid brightness!");
        }


        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
        tempPanel.add(tempLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==resizeButton){
            // check if file is selected or not
            if(file == null){
                this.getContentPane().removeAll();
                JLabel jLabel = new JLabel("No file selected!");
                jLabel.setBounds(300,180,100,100);
                this.add(jLabel);
                this.mainPanel();
                this.revalidate();
                this.repaint();
            }
            else {
                // remove last panel and create resize panel
                this.getContentPane().removeAll();
                this.resizePanel();
                this.revalidate();
                this.repaint();
            }

        }else if(e.getSource()== showImageButton){
            // check if file is selected or not
            if(file == null){
                this.getContentPane().removeAll();
                JLabel jLabel = new JLabel("No file selected!");
                jLabel.setBounds(300,180,100,100);
                this.add(jLabel);
                this.mainPanel();
                this.revalidate();
                this.repaint();
            }
            else {
                this.showOriginalImage();
            }

        }else if(e.getSource()==grayscaleButton){
            // check if file is selected or not
            if(file == null){
                this.getContentPane().removeAll();
                JLabel jLabel = new JLabel("No file selected!");
                jLabel.setBounds(300,180,100,100);
                this.add(jLabel);
                this.mainPanel();
                this.revalidate();
                this.repaint();
            }
            else {
                this.grayScaleImage();
            }

        }else if(e.getSource()== showResizeButton){
            // check scales to be valid number
            try {
                showResizeImage(Integer.parseInt(widthTextField.getText()), Integer.parseInt(heightTextField.getText()));
            }
            catch (Exception ex){
                this.getContentPane().removeAll();
                JLabel jLabel = new JLabel("Invalid input!");
                jLabel.setBounds(320,170,100,100);
                this.add(jLabel);
                this.resizePanel();
                this.revalidate();
                this.repaint();
            }

        }else if(e.getSource()==brightnessButton){
            // check if file is selected or not
            if(file == null){
                this.getContentPane().removeAll();
                JLabel jLabel = new JLabel("No file selected!");
                jLabel.setBounds(300,180,100,100);
                this.add(jLabel);
                this.mainPanel();
                this.revalidate();
                this.repaint();
            }
            else {
                // remove last panel and create brightness panel
                this.getContentPane().removeAll();
                this.brightnessPanel();
                this.revalidate();
                this.repaint();
            }

        }else if(e.getSource()== showBrightnessButton){
            // check brightness to be valid number
            try {
                brightenFactor = Float.parseFloat(brightnessTextField.getText());
                showBrightnessImage(brightenFactor);
            } catch (Exception ex) {
                this.getContentPane().removeAll();
                JLabel jLabel = new JLabel("Invalid input!");
                jLabel.setBounds(320,170,100,100);
                this.add(jLabel);
                this.brightnessPanel();
                this.revalidate();
                this.repaint();
            }

        }else if(e.getSource()== selectFileButton){
            // remove last panel and create choose file panel
            this.getContentPane().removeAll();
            this.chooseFileImage();
            this.revalidate();
            this.repaint();

        }else if(e.getSource()==closeButton){
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }

        else if(e.getSource()==backButton){
            // remove last panel and create main panel
            this.getContentPane().removeAll();
            this.mainPanel();
            this.revalidate();
            this.repaint();
        }

        else if(e.getSource() == applySize){
            try {
                w = Integer.parseInt((widthTextField.getText()));
                h = Integer.parseInt((heightTextField.getText()));
            }
            catch (Exception ex){
                this.getContentPane().removeAll();
                JLabel jLabel = new JLabel("Invalid input!");
                jLabel.setBounds(320,170,100,100);
                this.add(jLabel);
                this.resizePanel();
                this.revalidate();
                this.repaint();
            }
        }
    }
}