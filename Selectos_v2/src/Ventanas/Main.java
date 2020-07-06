package Ventanas;
import Clases.Compra;
import Clases.LectorCSV;
import SQL.ConexionSQL;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

import Ordenar.OrdenaProdNomAsc;
import Ordenar.OrdenaProdNomDesc;
import java.io.PrintWriter;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author AA2020
 */
public class Main extends javax.swing.JFrame {
    ConexionSQL conectar;
    public ArrayList<Compra> lCompra;
    DefaultTableModel modeloCompra;
    int p;
    
    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/icon.png")).getImage());
        this.jTabbedPane1.setEnabledAt(1, false);
        this.jTabbedPane1.setEnabledAt(2, false);
        this.jTabbedPane1.setEnabledAt(3, false);
        lCompra = new ArrayList<>();
        this.modeloCompra = (DefaultTableModel) this.jTabCompra.getModel();

        this.CargarJTabCompra();
        conectar = new ConexionSQL();
        conectar.ConnectDataBase();
    }

    
    private void CargarJTabCompra(){
        this.modeloCompra.getDataVector().removeAllElements();
        this.modeloCompra.setRowCount(0);
        for (int i = 0; i < this.lCompra.size(); i++) {
            String [] registroPrds = {this.lCompra.get(i).getProductoNombre(),
                                                    Integer.toString(this.lCompra.get(i).getProductoCantidad()),
                                                    Double.toString(this.lCompra.get(i).getProductoPrecio()),
                                                    Double.toString(this.lCompra.get(i).getSubTotal())
            };
            this.modeloCompra.addRow(registroPrds);
        } 
    }
    
    
    public String rutaCSV = "";
    public void CSV()
    {
        JFileChooser e = new JFileChooser();
        e.showOpenDialog(this);
        File archivo = e.getSelectedFile();
        if(archivo != null){
            this.rutaCSV = archivo.getAbsolutePath();
        }
        this.txtRuta.setText(rutaCSV);
        LectorCSV lector = new LectorCSV();
        ArrayList<ArrayList<String>> datos = lector.leer(this.rutaCSV);
        
        lCompra = new ArrayList<>();
        for(int i = 0; i < datos.size(); i++){
            String[] registroC = datos.get(i).toArray(new String[datos.get(i).size()]);
            this.modeloCompra.addRow(registroC);
            this.lCompra.add(new Compra(registroC[0], Integer.parseInt(registroC[1]), Double.parseDouble(registroC[2]), Double.parseDouble(registroC[3])));

        }
        JOptionPane.showMessageDialog(null, "Importado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
        this.lblTotalPagar.setText(TotalPagar());
    }
    
    
    public String TotalPagar(){
        int contar = jTabCompra.getRowCount();
        double sumar = 0;
        for (int i = 0; i < contar; i++) {
            sumar += Double.parseDouble(jTabCompra.getValueAt(i, 3).toString());
        }
        return String.format("%.2f", sumar);
    }
    
    
    public void PDF()
    {
        String filename = "Factura_SuperSelectos.pdf";

        try {
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();
            doc.addPage(page);
            int x = 80, y = 730;
            PDPageContentStream contents = new PDPageContentStream(doc, page);
            //encabezado
            contents.beginText();
            contents.setFont(PDType1Font.COURIER_BOLD, 18);
            contents.newLineAtOffset(80, 755);
            contents.showText("LISTA DE COMPRAS");
            contents.endText();
            //encabezados
            contents.beginText();
            contents.setFont(PDType1Font.COURIER_BOLD, 10);
            contents.newLineAtOffset(80, 735);
            contents.showText("PRODUCTO            || CANTIDAD     || PRECIO UNITARIO       || TOTAL A PAGAR: $" + this.lblTotalPagar.getText());
            contents.endText();
            //relleno
            String linea = "";
            String encabezados = "";

                int i = 1;
                if (y - 30 < 60) {
                    contents.close();
                    y = 730;
                    page = new PDPage();
                    doc.addPage(page);
                    contents = new PDPageContentStream(doc, page);
                }
                x = 80;
                contents.beginText();
                contents.setFont(PDType1Font.COURIER_BOLD, 8);
                y = y - 15;
                contents.newLineAtOffset(x, y);
                contents.showText(encabezados);
                contents.endText();

                for (Compra produc : lCompra) {

                    if (i == 1) {
                        linea = produc.getProductoNombre()+ " || " + produc.getProductoCantidad()+ " || $" + produc.getProductoPrecio();
                        contents.beginText();
                        contents.setFont(PDType1Font.COURIER, 8);
                        x = x + 0;
                        contents.newLineAtOffset(x, y);
                        contents.showText(linea);
                        contents.endText();
                    } else {
                        linea = produc.getProductoNombre() + " || " + produc.getProductoCantidad() + " || $" + produc.getProductoPrecio();
                        contents.beginText();
                        contents.setFont(PDType1Font.COURIER, 8);
                        y = y - 15;
                        contents.newLineAtOffset(80, y);
                        contents.showText(linea);
                        contents.endText();
                    }
                    i++;
                }
            contents.close();
            doc.save(filename);
            doc.close();

            JOptionPane.showMessageDialog(null, "Generado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ee) {
            JOptionPane.showMessageDialog(null, "Un error inesperado ha ocurrido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanLogin = new javax.swing.JPanel();
        banner = new javax.swing.JLabel();
        jPanINGRESADATOS = new javax.swing.JPanel();
        lblContrasenia = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jPasswordField = new javax.swing.JPasswordField();
        btnLOGIN = new javax.swing.JButton();
        jPanProductos = new javax.swing.JPanel();
        lblBanner = new javax.swing.JLabel();
        jPanREALIZACOMPRAS = new javax.swing.JPanel();
        btnGENERARLISTA = new javax.swing.JButton();
        btnCSV = new javax.swing.JButton();
        jPanLista = new javax.swing.JPanel();
        lblBannner = new javax.swing.JLabel();
        jPanREVISARPEDIDO = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabCompra = new javax.swing.JTable();
        txtRuta = new javax.swing.JTextField();
        lblTotalPagar = new javax.swing.JLabel();
        btnAtoZ = new javax.swing.JButton();
        btnZtoA = new javax.swing.JButton();
        lblTOT = new javax.swing.JLabel();
        lblRuta = new javax.swing.JLabel();
        jPanFactura = new javax.swing.JPanel();
        lblBANNER = new javax.swing.JLabel();
        jPanFACTURAREXPORTAR = new javax.swing.JPanel();
        btnPDF = new javax.swing.JButton();
        btnDataBase = new javax.swing.JButton();
        btnXML = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Super Selectos | Java");

        jTabbedPane1.setBackground(new java.awt.Color(51, 102, 0));
        jTabbedPane1.setForeground(new java.awt.Color(102, 153, 0));
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 16)); // NOI18N

        jPanLogin.setBackground(new java.awt.Color(87, 111, 39));

        banner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/bannerfinal.png"))); // NOI18N

        jPanINGRESADATOS.setBackground(new java.awt.Color(115, 143, 59));
        jPanINGRESADATOS.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ingresa tus datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanINGRESADATOS.setForeground(new java.awt.Color(102, 153, 0));

        lblContrasenia.setBackground(new java.awt.Color(255, 255, 255));
        lblContrasenia.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 36)); // NOI18N
        lblContrasenia.setForeground(new java.awt.Color(255, 255, 255));
        lblContrasenia.setText("Contraseña:");

        lblUsuario.setBackground(new java.awt.Color(255, 255, 255));
        lblUsuario.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 36)); // NOI18N
        lblUsuario.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuario.setText("Usuario:");

        txtUsuario.setFont(new java.awt.Font("Tw Cen MT", 3, 36)); // NOI18N

        jPasswordField.setFont(new java.awt.Font("Tw Cen MT", 3, 36)); // NOI18N

        btnLOGIN.setBackground(new java.awt.Color(87, 111, 39));
        btnLOGIN.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 36)); // NOI18N
        btnLOGIN.setForeground(new java.awt.Color(255, 255, 255));
        btnLOGIN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iniciar-sesion.png"))); // NOI18N
        btnLOGIN.setText("INGRESAR");
        btnLOGIN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLOGINActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanINGRESADATOSLayout = new javax.swing.GroupLayout(jPanINGRESADATOS);
        jPanINGRESADATOS.setLayout(jPanINGRESADATOSLayout);
        jPanINGRESADATOSLayout.setHorizontalGroup(
            jPanINGRESADATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanINGRESADATOSLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanINGRESADATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnLOGIN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanINGRESADATOSLayout.createSequentialGroup()
                        .addGroup(jPanINGRESADATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblContrasenia)
                            .addComponent(lblUsuario))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanINGRESADATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUsuario)
                            .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 940, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(89, Short.MAX_VALUE))
        );
        jPanINGRESADATOSLayout.setVerticalGroup(
            jPanINGRESADATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanINGRESADATOSLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanINGRESADATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUsuario))
                .addGap(18, 18, 18)
                .addGroup(jPanINGRESADATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblContrasenia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPasswordField))
                .addGap(18, 18, 18)
                .addComponent(btnLOGIN)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanLoginLayout = new javax.swing.GroupLayout(jPanLogin);
        jPanLogin.setLayout(jPanLoginLayout);
        jPanLoginLayout.setHorizontalGroup(
            jPanLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(banner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1311, Short.MAX_VALUE)
            .addGroup(jPanLoginLayout.createSequentialGroup()
                .addComponent(jPanINGRESADATOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanLoginLayout.setVerticalGroup(
            jPanLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanLoginLayout.createSequentialGroup()
                .addComponent(banner)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanINGRESADATOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(349, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("LOGIN", jPanLogin);

        jPanProductos.setBackground(new java.awt.Color(87, 111, 39));
        jPanProductos.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 24)); // NOI18N

        lblBanner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/bannerfinal.png"))); // NOI18N

        jPanREALIZACOMPRAS.setBackground(new java.awt.Color(115, 143, 59));
        jPanREALIZACOMPRAS.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Realiza tus compras", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanREALIZACOMPRAS.setForeground(new java.awt.Color(102, 153, 0));

        btnGENERARLISTA.setBackground(new java.awt.Color(87, 111, 39));
        btnGENERARLISTA.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 36)); // NOI18N
        btnGENERARLISTA.setForeground(new java.awt.Color(255, 255, 255));
        btnGENERARLISTA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/menu.png"))); // NOI18N
        btnGENERARLISTA.setText("GENERAR LISTA");
        btnGENERARLISTA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGENERARLISTAActionPerformed(evt);
            }
        });

        btnCSV.setBackground(new java.awt.Color(87, 111, 39));
        btnCSV.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 36)); // NOI18N
        btnCSV.setForeground(new java.awt.Color(255, 255, 255));
        btnCSV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/csv.png"))); // NOI18N
        btnCSV.setText("IMPORTAR CSV");
        btnCSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCSVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanREALIZACOMPRASLayout = new javax.swing.GroupLayout(jPanREALIZACOMPRAS);
        jPanREALIZACOMPRAS.setLayout(jPanREALIZACOMPRASLayout);
        jPanREALIZACOMPRASLayout.setHorizontalGroup(
            jPanREALIZACOMPRASLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanREALIZACOMPRASLayout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addGroup(jPanREALIZACOMPRASLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCSV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGENERARLISTA, javax.swing.GroupLayout.PREFERRED_SIZE, 1109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(74, 74, 74))
        );
        jPanREALIZACOMPRASLayout.setVerticalGroup(
            jPanREALIZACOMPRASLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanREALIZACOMPRASLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGENERARLISTA)
                .addGap(18, 18, 18)
                .addComponent(btnCSV)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanProductosLayout = new javax.swing.GroupLayout(jPanProductos);
        jPanProductos.setLayout(jPanProductosLayout);
        jPanProductosLayout.setHorizontalGroup(
            jPanProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanProductosLayout.createSequentialGroup()
                .addGroup(jPanProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanREALIZACOMPRAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBanner))
                .addGap(0, 34, Short.MAX_VALUE))
        );
        jPanProductosLayout.setVerticalGroup(
            jPanProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanProductosLayout.createSequentialGroup()
                .addComponent(lblBanner)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanREALIZACOMPRAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(407, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("PRODUCTOS", jPanProductos);

        jPanLista.setBackground(new java.awt.Color(87, 111, 39));

        lblBannner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/bannerfinal.png"))); // NOI18N

        jPanREVISARPEDIDO.setBackground(new java.awt.Color(115, 143, 59));
        jPanREVISARPEDIDO.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Revisar compras", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanREVISARPEDIDO.setForeground(new java.awt.Color(102, 153, 0));

        jTabCompra.setBackground(new java.awt.Color(62, 81, 24));
        jTabCompra.setBorder(new javax.swing.border.MatteBorder(null));
        jTabCompra.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jTabCompra.setForeground(new java.awt.Color(255, 255, 255));
        jTabCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PRODUCTO", "CANTIDAD", "PRECIO ($)", "SUBTOTAL ($)"
            }
        ));
        jScrollPane1.setViewportView(jTabCompra);

        txtRuta.setBackground(new java.awt.Color(115, 143, 59));
        txtRuta.setFont(new java.awt.Font("Tw Cen MT Condensed", 3, 18)); // NOI18N
        txtRuta.setForeground(new java.awt.Color(0, 0, 0));

        lblTotalPagar.setFont(new java.awt.Font("Tw Cen MT Condensed", 3, 36)); // NOI18N
        lblTotalPagar.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalPagar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        btnAtoZ.setBackground(new java.awt.Color(87, 111, 39));
        btnAtoZ.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 36)); // NOI18N
        btnAtoZ.setForeground(new java.awt.Color(255, 255, 255));
        btnAtoZ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ordenar-az.png"))); // NOI18N
        btnAtoZ.setText("ORDENAR DE LA \"A\" A LA \"Z\"");
        btnAtoZ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtoZActionPerformed(evt);
            }
        });

        btnZtoA.setBackground(new java.awt.Color(87, 111, 39));
        btnZtoA.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 36)); // NOI18N
        btnZtoA.setForeground(new java.awt.Color(255, 255, 255));
        btnZtoA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ordenar-za.png"))); // NOI18N
        btnZtoA.setText("ORDENAR DE LA \"Z\" A LA \"A\"");
        btnZtoA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZtoAActionPerformed(evt);
            }
        });

        lblTOT.setFont(new java.awt.Font("Tw Cen MT Condensed", 3, 36)); // NOI18N
        lblTOT.setForeground(new java.awt.Color(0, 0, 0));
        lblTOT.setText("Total a pagar: $");

        lblRuta.setFont(new java.awt.Font("Tw Cen MT Condensed", 3, 18)); // NOI18N
        lblRuta.setForeground(new java.awt.Color(0, 0, 0));
        lblRuta.setText("Ruta:");

        javax.swing.GroupLayout jPanREVISARPEDIDOLayout = new javax.swing.GroupLayout(jPanREVISARPEDIDO);
        jPanREVISARPEDIDO.setLayout(jPanREVISARPEDIDOLayout);
        jPanREVISARPEDIDOLayout.setHorizontalGroup(
            jPanREVISARPEDIDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanREVISARPEDIDOLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanREVISARPEDIDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAtoZ)
                    .addComponent(btnZtoA))
                .addGap(248, 248, 248)
                .addGroup(jPanREVISARPEDIDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanREVISARPEDIDOLayout.createSequentialGroup()
                        .addComponent(lblTOT, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalPagar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanREVISARPEDIDOLayout.createSequentialGroup()
                        .addComponent(lblRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(txtRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanREVISARPEDIDOLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanREVISARPEDIDOLayout.setVerticalGroup(
            jPanREVISARPEDIDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanREVISARPEDIDOLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanREVISARPEDIDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanREVISARPEDIDOLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanREVISARPEDIDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTOT)
                            .addComponent(lblTotalPagar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanREVISARPEDIDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRuta))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanREVISARPEDIDOLayout.createSequentialGroup()
                        .addComponent(btnAtoZ)
                        .addGap(18, 18, 18)
                        .addComponent(btnZtoA)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanListaLayout = new javax.swing.GroupLayout(jPanLista);
        jPanLista.setLayout(jPanListaLayout);
        jPanListaLayout.setHorizontalGroup(
            jPanListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanREVISARPEDIDO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblBannner, javax.swing.GroupLayout.DEFAULT_SIZE, 1311, Short.MAX_VALUE)
        );
        jPanListaLayout.setVerticalGroup(
            jPanListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanListaLayout.createSequentialGroup()
                .addComponent(lblBannner)
                .addGap(12, 12, 12)
                .addComponent(jPanREVISARPEDIDO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(170, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("LISTA DE COMPRAS", jPanLista);

        jPanFactura.setBackground(new java.awt.Color(87, 111, 39));

        lblBANNER.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/bannerfinal.png"))); // NOI18N

        jPanFACTURAREXPORTAR.setBackground(new java.awt.Color(115, 143, 59));
        jPanFACTURAREXPORTAR.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Facturar y exportar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanFACTURAREXPORTAR.setForeground(new java.awt.Color(102, 153, 0));

        btnPDF.setBackground(new java.awt.Color(87, 111, 39));
        btnPDF.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 36)); // NOI18N
        btnPDF.setForeground(new java.awt.Color(255, 255, 255));
        btnPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pdf.png"))); // NOI18N
        btnPDF.setText("PDF");
        btnPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPDFActionPerformed(evt);
            }
        });

        btnDataBase.setBackground(new java.awt.Color(87, 111, 39));
        btnDataBase.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 36)); // NOI18N
        btnDataBase.setForeground(new java.awt.Color(255, 255, 255));
        btnDataBase.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/base-de-datos.png"))); // NOI18N
        btnDataBase.setText("SUBIR A LA BASE DE DATOS");
        btnDataBase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataBaseActionPerformed(evt);
            }
        });

        btnXML.setBackground(new java.awt.Color(87, 111, 39));
        btnXML.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 36)); // NOI18N
        btnXML.setForeground(new java.awt.Color(255, 255, 255));
        btnXML.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/www.png"))); // NOI18N
        btnXML.setText("XML (HTML)");
        btnXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXMLActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(0, 0, 0));
        btnSalir.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 36)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(193, 32, 32));
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanFACTURAREXPORTARLayout = new javax.swing.GroupLayout(jPanFACTURAREXPORTAR);
        jPanFACTURAREXPORTAR.setLayout(jPanFACTURAREXPORTARLayout);
        jPanFACTURAREXPORTARLayout.setHorizontalGroup(
            jPanFACTURAREXPORTARLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanFACTURAREXPORTARLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(jPanFACTURAREXPORTARLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanFACTURAREXPORTARLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnPDF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDataBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXML, javax.swing.GroupLayout.PREFERRED_SIZE, 1109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanFACTURAREXPORTARLayout.createSequentialGroup()
                        .addGap(402, 402, 402)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanFACTURAREXPORTARLayout.setVerticalGroup(
            jPanFACTURAREXPORTARLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanFACTURAREXPORTARLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPDF)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDataBase)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXML)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanFacturaLayout = new javax.swing.GroupLayout(jPanFactura);
        jPanFactura.setLayout(jPanFacturaLayout);
        jPanFacturaLayout.setHorizontalGroup(
            jPanFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBANNER, javax.swing.GroupLayout.DEFAULT_SIZE, 1311, Short.MAX_VALUE)
            .addGroup(jPanFacturaLayout.createSequentialGroup()
                .addComponent(jPanFACTURAREXPORTAR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanFacturaLayout.setVerticalGroup(
            jPanFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanFacturaLayout.createSequentialGroup()
                .addComponent(lblBANNER)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanFACTURAREXPORTAR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(269, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("FACTURA", jPanFactura);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCSVActionPerformed
        this.modeloCompra.getDataVector().removeAllElements();
        this.CSV();
    }//GEN-LAST:event_btnCSVActionPerformed

    private void btnGENERARLISTAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGENERARLISTAActionPerformed
        this.lblTOT.setVisible(true);
        modeloCompra= (DefaultTableModel) this.jTabCompra.getModel();
        
        lCompra= new ArrayList<>();
        this.lCompra.add(new Compra("Sazonador Continental de Pollo 180G".toUpperCase(), 1, 1.49, 1));
        this.lCompra.add(new Compra("Mostaza del Chef 24 8onz".toUpperCase(), 1, 1.07, 1));
        this.lCompra.add(new Compra("Pan Pullman Lido 550G".toUpperCase(), 1, 1.50, 1));
        this.lCompra.add(new Compra("Leche en polvo Pinito Dos Pinos 350G".toUpperCase(), 1, 3.11, 1));
        this.lCompra.add(new Compra("2 pack aceite mazola doy pack 750ml".toUpperCase(), 1, 2.90, 1));
        this.lCompra.add(new Compra("harina pancake instantaneo vainilla dany".toUpperCase(), 1, 1.65, 1));
        this.lCompra.add(new Compra("6 pack yogurt yes cool galleta fresa 690g".toUpperCase(), 1, 1.49, 1));
        this.lCompra.add(new Compra("2 pack atun bahia en agua 330g".toUpperCase(), 1, 2.59, 1));
        this.lCompra.add(new Compra("costilla de cerdo riblets libra".toUpperCase(), 2, 2.46, 1));
        this.lCompra.add(new Compra("lavaplatos axion crema limon 660g".toUpperCase(), 1, 1.99, 1));
        this.lCompra.add(new Compra("servilleta basica dany 100 unidades".toUpperCase(), 1, 0.55, 1));
        this.lCompra.add(new Compra("margarina mazola ajo hierbas 400g".toUpperCase(), 1, 1.44, 1));
        this.lCompra.add(new Compra("galleta gama club max 324g".toUpperCase(), 1, 1.16, 1));
        this.lCompra.add(new Compra("3 pack jabon palmolive almendra y omega 3".toUpperCase(), 1, 1.90, 1));
        this.lCompra.add(new Compra("3 pack pastillas terror surtida 1/16 und".toUpperCase(), 1, 1.49,1));
        this.lCompra.add(new Compra("crema dental colgate repara/diaria 97g".toUpperCase(), 1, 2.95, 1));
        this.lCompra.add(new Compra("papel higienico rosal verde 12 rollos".toUpperCase(), 1, 6.63,1));
        this.lCompra.add(new Compra("jamon virginia fud al vaco 290 gramos".toUpperCase(), 1, 2.27,1));
        this.lCompra.add(new Compra("chorizo toledo argentino paquete 460g".toUpperCase(), 1, 3.55,1));
        this.lCompra.add(new Compra("costillon especial de res libra".toUpperCase(), 3, 1.10,1));
        this.lCompra.add(new Compra("canela molida fco mccormick 55gr/12".toUpperCase(), 1, 2.01,1));
        this.lCompra.add(new Compra("detergente xedex suavizante brisa 500gr".toUpperCase(), 1, 1.34,1));
        this.lCompra.add(new Compra("detergente pvo surf fueza del sol 900g".toUpperCase(), 1, 1.10,1));
        this.lCompra.add(new Compra("3 pack jabon zixx rosado eliminador 425g".toUpperCase(), 1, 2.63,1));
        this.lCompra.add(new Compra("suavitel adios planchado anochecer 720ml".toUpperCase(), 1, 1.50,1));
        this.lCompra.add(new Compra("rodillon pellets cebo mata ratas 25gr".toUpperCase(), 2, 1.00,1));
        this.lCompra.add(new Compra("espaguetti con queso la moderna 20/200gr".toUpperCase(), 1, 1.14,1));
        this.lCompra.add(new Compra("rapiditas integrales 10 piezas bimbo 250".toUpperCase(), 1, 1.79,1));
        this.lCompra.add(new Compra("avena mosh 3 minutos 50 400 gramos a050".toUpperCase(), 1, 1.21,1));
        this.lCompra.add(new Compra("mayonesa mayoliva libre colesterol 400g".toUpperCase(), 1, 1.86,1));
        this.lCompra.add(new Compra("sandwich spread mccormick doypack 200ml".toUpperCase(), 1, 1.45,1));
        this.lCompra.add(new Compra("galletas cookies and cream oreo 432gr".toUpperCase(), 1, 3.07,1));
        this.lCompra.add(new Compra("crema de mariscos maggi 80g".toUpperCase(), 1, 0.55,1));
        this.lCompra.add(new Compra("2 pack salchicha hot dog toledo 920g".toUpperCase(), 1, 3.08,1));
        this.lCompra.add(new Compra("aceite oliva selectos extra virgen 120ml".toUpperCase(), 1, 1.70,1));
        this.lCompra.add(new Compra("pop corn grano as de oro 400 grs".toUpperCase(), 2, 0.88,1));
        this.lCompra.add(new Compra("azucar morena 2.5kg".toUpperCase(), 1, 2.50,1));
        this.lCompra.add(new Compra("chao mein catay con soya robertoni 230g".toUpperCase(), 1, 1.00,1));
        this.lCompra.add(new Compra("desinfectante azistin forta manzana 900ml".toUpperCase(), 1, 1.85,1));
        this.lCompra.add(new Compra("queso mozzarella lactolac 200g".toUpperCase(), 1, 1.90,1));
        this.lCompra.add(new Compra("queso mozzarella san julian 200g".toUpperCase(), 1, 1.65,1));
        this.lCompra.add(new Compra("carne molida premium de res".toUpperCase(), 1, 2.05,1));
        this.lCompra.add(new Compra("carne de brazuelo de cerdo libra".toUpperCase(), 1, 2.35,1));
        this.lCompra.add(new Compra("pechuga con hueso de pollo indio libra".toUpperCase(), 3, 1.45,1));
        
        this.RecargarTabla();
        this.lblTotalPagar.setText(TotalPagar());
        JOptionPane.showMessageDialog(null, "Lista generada correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_btnGENERARLISTAActionPerformed

    private void btnAtoZActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtoZActionPerformed
          OrdenaProdNomAsc oNA= new OrdenaProdNomAsc();
          this.lCompra.sort(oNA);
          this.CargarJTabCompra();
    }//GEN-LAST:event_btnAtoZActionPerformed

    private void btnZtoAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZtoAActionPerformed
        OrdenaProdNomDesc oND= new OrdenaProdNomDesc();
        this.lCompra.sort(oND);
        this.CargarJTabCompra();
    }//GEN-LAST:event_btnZtoAActionPerformed

    private void btnPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFActionPerformed
        this.PDF();
    }//GEN-LAST:event_btnPDFActionPerformed

    private void btnDataBaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataBaseActionPerformed
        try {
            lCompra.forEach((produc) -> {
                conectar.InsertarProducto(produc.getProductoNombre(), produc.getProductoCantidad(), produc.getProductoPrecio(), produc.getSubTotal());
            });
        } catch (Exception e) {
            System.out.println(e);

        }
        JOptionPane.showMessageDialog(null, "¡Datos cargados a la base de datos exitosamente! :)", "Información", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnDataBaseActionPerformed

    private void btnXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXMLActionPerformed
        PrintWriter pw = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        File selectedFile = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            System.out.println("Archivo seleccionado: " + selectedFile.getAbsolutePath());
        }
        try {
            pw = new PrintWriter(new File(selectedFile.getAbsolutePath()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Un error inesperado ha ocurrido", "Error", JOptionPane.ERROR_MESSAGE);
        }

        StringBuilder strLinea = new StringBuilder();
        strLinea.append("<html><body><font face=\"verdana\"><center><h1>Lista de Compras - Super Selectos</h1></font></center><center><table border='2'>" + "\n");
        strLinea.append("<tr><td><b>PRODUCTO</td><td><b>CANTIDAD</td><td><b>PRECIO UNITARIO</td></tr>");
        for (int i = 0; i < lCompra.size(); i++) {
            strLinea.append("<tr>" + "\n");
            strLinea.append("<td>");
            strLinea.append(lCompra.get(i).getProductoNombre());
            strLinea.append("</td>" + "\n");

            strLinea.append("<td>");
            strLinea.append(Integer.toString(lCompra.get(i).getProductoCantidad()));
            strLinea.append("</td>" + "\n");

            strLinea.append("<td>");
            strLinea.append("$").append(Double.toString(lCompra.get(i).getProductoPrecio()));
            strLinea.append("</td>" + "\n");
        }
        strLinea.append("</table></center>" + "\n");
        strLinea.append("\n");
        strLinea.append("<br>");
        strLinea.append("<center><font face=\"verdana\"><font color=\"red\"><h2><b>TOTAL A PAGAR: $");
        strLinea.append(TotalPagar());
        strLinea.append("</center></font></h2></b>");
        strLinea.append("</body></html>");
        pw.write(strLinea.toString());
        pw.close();
    }//GEN-LAST:event_btnXMLActionPerformed

    private void btnLOGINActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLOGINActionPerformed
        if (this.jPasswordField.getText().equals("super2020")) {
            this.jTabbedPane1.setSelectedIndex(1);
            this.jTabbedPane1.setEnabledAt(0, false);
            this.jTabbedPane1.setEnabledAt(1, true);
            this.jTabbedPane1.setEnabledAt(2, true);
            this.jTabbedPane1.setEnabledAt(3, true);
        }else{
        JOptionPane.showMessageDialog(this, "La CONTRASEÑA o el NOMBRE DE USUARIO es incorrecto, por favor verifique.", "Validación: Error al ingresar", 1);
        }
    }//GEN-LAST:event_btnLOGINActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void RecargarTabla()
    {
        this.modeloCompra.getDataVector().removeAllElements();
        this.modeloCompra.setRowCount(0);
        for(int i=0; i<this.lCompra.size(); i++)
        {
            String[] registro = { this.lCompra.get(i).getProductoNombre(), Integer.toString(this.lCompra.get(i).getProductoCantidad()),
                                  Double.toString(this.lCompra.get(i).getProductoPrecio()), Double.toString(this.lCompra.get(i).getSubTotal())};
            modeloCompra.addRow(registro);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel banner;
    private javax.swing.JButton btnAtoZ;
    private javax.swing.JButton btnCSV;
    private javax.swing.JButton btnDataBase;
    private javax.swing.JButton btnGENERARLISTA;
    private javax.swing.JButton btnLOGIN;
    private javax.swing.JButton btnPDF;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnXML;
    private javax.swing.JButton btnZtoA;
    private javax.swing.JPanel jPanFACTURAREXPORTAR;
    private javax.swing.JPanel jPanFactura;
    private javax.swing.JPanel jPanINGRESADATOS;
    private javax.swing.JPanel jPanLista;
    private javax.swing.JPanel jPanLogin;
    private javax.swing.JPanel jPanProductos;
    private javax.swing.JPanel jPanREALIZACOMPRAS;
    private javax.swing.JPanel jPanREVISARPEDIDO;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTabCompra;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblBANNER;
    private javax.swing.JLabel lblBanner;
    private javax.swing.JLabel lblBannner;
    private javax.swing.JLabel lblContrasenia;
    private javax.swing.JLabel lblRuta;
    private javax.swing.JLabel lblTOT;
    private javax.swing.JLabel lblTotalPagar;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTextField txtRuta;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables

}
