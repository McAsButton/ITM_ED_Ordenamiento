import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.WindowConstants;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmOrdenamiento extends JFrame {

    private JButton btnOrdenarBurbuja;
    private JButton btnOrdenarRapido;
    private JButton btnOrdenarInsercion;
    private JToolBar tbOrdenamiento;
    private JComboBox<String> cmbCriterio;
    private JTextField txtTiempo;
    private JButton btnBuscar;
    private JTextField txtBuscar;

    private JTable tblDocumentos;
    private Timer timer;

    public FrmOrdenamiento() {

        tbOrdenamiento = new JToolBar();
        btnOrdenarBurbuja = new JButton();
        btnOrdenarInsercion = new JButton();
        btnOrdenarRapido = new JButton();
        cmbCriterio = new JComboBox<String>();
        txtTiempo = new JTextField();

        btnBuscar = new JButton();
        txtBuscar = new JTextField();

        tblDocumentos = new JTable();

        setSize(700, 400);
        setTitle("Ordenamiento Documentos");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        btnOrdenarBurbuja.setIcon(new ImageIcon(getClass().getResource("/Icon/Ordenar.png")));
        btnOrdenarBurbuja.setToolTipText("Ordenar Burbuja");
        btnOrdenarBurbuja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnOrdenarBurbujaClick(evt);
            }
        });
        tbOrdenamiento.add(btnOrdenarBurbuja);

        btnOrdenarRapido.setIcon(new ImageIcon(getClass().getResource("/Icon/OrdenarRapido.png"))); // cspell: disable-line
        btnOrdenarRapido.setToolTipText("Ordenar Rápido");
        btnOrdenarRapido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnOrdenarRapidoClick(evt);
            }
        });
        tbOrdenamiento.add(btnOrdenarRapido);

        btnOrdenarInsercion.setIcon(new ImageIcon(getClass().getResource("/Icon/OrdenarInsercion.png"))); // cspell: disable-line
        btnOrdenarInsercion.setToolTipText("Ordenar Inserción");
        btnOrdenarInsercion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnOrdenarInsercionClick(evt);
            }
        });
        tbOrdenamiento.add(btnOrdenarInsercion);

        cmbCriterio.setModel(new DefaultComboBoxModel<String>(
                new String[] { "Nombre Completo, Tipo de Documento", "Tipo de Documento, Nombre Completo" }));
        tbOrdenamiento.add(cmbCriterio);
        tbOrdenamiento.add(txtTiempo);

        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/Icon/Buscar.png")));
        btnBuscar.setToolTipText("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnBuscar(evt);
            }
        });
        tbOrdenamiento.add(btnBuscar);
        tbOrdenamiento.add(txtBuscar);

        getContentPane().add(tbOrdenamiento, BorderLayout.NORTH);

        JScrollPane spDocumentos=new JScrollPane(tblDocumentos);
        getContentPane().add(spDocumentos, BorderLayout.CENTER);

        String nombreArchivo = System.getProperty("user.dir")
                + "/Proyectos/Ordenamiento/src/Data/Datos.csv";
        Documento.obtenerDatosDesdeArchivo(nombreArchivo);
        Documento.mostrarDatos(tblDocumentos);

    }

    private void btnOrdenarBurbujaClick(ActionEvent evt) {
        // if (cmbCriterio.getSelectedIndex() >= 0) {
        //     Util.iniciarCronometro();
        //     Documento.ordenarBurbuja(cmbCriterio.getSelectedIndex());
        //     txtTiempo.setText(Util.getTextoTiempoCronometro());
        //     Documento.mostrarDatos(tblDocumentos);
        // }

        if (cmbCriterio.getSelectedIndex() >= 0) {
            Util.iniciarCronometro();

            // Crear un SwingWorker para realizar la ordenación en segundo plano
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    // Iniciar un Timer que actualiza el campo de tiempo cada 100 ms
                    iniciarActualizacionTiempo();

                    // Realizar el proceso de ordenamiento
                    Documento.ordenarBurbuja(cmbCriterio.getSelectedIndex());

                    return null;
                }

                @Override
                protected void done() {
                    detenerActualizacionTiempo();
                    Documento.mostrarDatos(tblDocumentos);
                }
            };

            worker.execute(); // Ejecutar el SwingWorker en segundo plano
        }
    }

    private void btnOrdenarRapidoClick(ActionEvent evt) {
        // if (cmbCriterio.getSelectedIndex() >= 0) {
        //     Util.iniciarCronometro();
        //     Documento.ordenarRapido(0, Documento.documentos.size()-1, cmbCriterio.getSelectedIndex());
        //     txtTiempo.setText(Util.getTextoTiempoCronometro());
        //     Documento.mostrarDatos(tblDocumentos);
        // }

        if (cmbCriterio.getSelectedIndex() >= 0) {
            Util.iniciarCronometro();

            // Crear un SwingWorker para realizar la ordenación en segundo plano
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    // Iniciar un Timer que actualiza el campo de tiempo cada 100 ms
                    iniciarActualizacionTiempo();

                    // Realizar el proceso de ordenamiento
                    Documento.ordenarRapido(0, Documento.documentos.size() - 1, cmbCriterio.getSelectedIndex());

                    return null;
                }

                @Override
                protected void done() {
                    detenerActualizacionTiempo();
                    Documento.mostrarDatos(tblDocumentos);
                }
            };

            worker.execute(); // Ejecutar el SwingWorker en segundo plano
        }
    }

    private void btnOrdenarInsercionClick(ActionEvent evt) {
        // if (cmbCriterio.getSelectedIndex() >= 0) {
        //     Util.iniciarCronometro();
        //     Documento.ordenarInsercion(cmbCriterio.getSelectedIndex());
        //     txtTiempo.setText(Util.getTextoTiempoCronometro());
        //     Documento.mostrarDatos(tblDocumentos);
        // }

        if (cmbCriterio.getSelectedIndex() >= 0) {
            Util.iniciarCronometro();

            // Crear un SwingWorker para realizar la ordenación en segundo plano
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    // Iniciar un Timer que actualiza el campo de tiempo cada 100 ms
                    iniciarActualizacionTiempo();

                    // Realizar el proceso de ordenamiento
                    Documento.ordenarInsercion(cmbCriterio.getSelectedIndex());

                    return null;
                }

                @Override
                protected void done() {
                    detenerActualizacionTiempo();
                    Documento.mostrarDatos(tblDocumentos);
                }
            };

            worker.execute(); // Ejecutar el SwingWorker en segundo plano
        }
    }

    private void btnBuscar(ActionEvent evt) {

    }

    private void iniciarActualizacionTiempo() {
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtTiempo.setText(Util.getTextoTiempoCronometro());
            }
        });
        timer.start();
    }

    private void detenerActualizacionTiempo() {
        if (timer != null) {
            timer.stop();
        }
        txtTiempo.setText(Util.getTextoTiempoCronometro());
    }

}