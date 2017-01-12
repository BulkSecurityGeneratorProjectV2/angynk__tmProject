package com.journaldev.processing.saveData;


import com.journaldev.hibernate.data.entity.*;
import com.journaldev.spring.service.ExcelReader;
import com.journaldev.spring.service.GisCargaService;
import com.journaldev.spring.service.NodoService;
import com.journaldev.spring.service.TipoDiaService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service("DataProcesorImpl")
public class DataProcesorImpl {


    private ExcelReader excelReader;

    @Autowired
    private GisCargaService gisCargaService;

    @Autowired
    private TipoDiaService tipoDiaService;

    @Autowired
    private NodoService nodoService;

    private String destination="C:\\temp\\";

    public DataProcesorImpl() {
        excelReader = new ExcelReader();
    }

    public boolean processDataFromFile(String fileName, InputStream in, Date fechaProgrmacion, Date fechaVigencia, String tipoDia, String descripcion) {
        copyFile(fileName,in);
        GisCarga gisCarga = saveGisCarga(fechaProgrmacion,fechaVigencia,descripcion);
        try {
            readExcelAndSaveData(destination,gisCarga,tipoDia);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println( gisCargaService.getGisCargaAll().size());
//        System.out.println( gisCargaService.getTrayectoAll().size());
//        System.out.println( gisCargaService.getArcoTiempoAll().size());
//        System.out.println( tipoDiaService.getTipoDiaAll().size());
//        System.out.println( tipoDiaService.getTipoDiaDetalleAll().size());
//        System.out.println( nodoService.getNodosAll().size());
        return false;

    }

    public GisCarga saveGisCarga(Date fechaProgrmacion, Date fechaVigencia,String descripcion){
        GisCarga gisCarga = new GisCarga(new Date(),fechaProgrmacion,fechaVigencia,descripcion);
       gisCargaService.addGisCarga(gisCarga);
        return gisCarga;
    }

    public Trayecto findOrSaveTrayecto(Row row){
        String trayectoId =  row.getCell(GisCargaDefinition.TRAYECTO).getStringCellValue();
        List<Trayecto> trayectoByIdentifier = gisCargaService.getTrayectoByIdentifier(trayectoId);
        if( trayectoByIdentifier.size() == 0 ){
            int linea = Integer.parseInt( row.getCell(GisCargaDefinition.LINEA ).getStringCellValue());
            Trayecto trayecto = new Trayecto( trayectoId, linea );
            gisCargaService.addTrayecto(trayecto);
            return trayecto;
        }
        return trayectoByIdentifier.get(0);
    }


    public void readExcelAndSaveData(String destination,GisCarga gisCarga, String tipoDiaD)throws IOException{
        try {
            FileInputStream fileInputStream = new FileInputStream(destination);
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
            HSSFSheet worksheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = worksheet.iterator();
            rowIterator.next();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if( row.getCell(0) != null ){
                    Trayecto trayecto = findOrSaveTrayecto(row);
                    TipoDiaDetalle tipoDia = findOrSaveTipoDia(row,tipoDiaD);
                    Nodo nodoInicial = findOrSaveNodo(row, GisCargaDefinition.NODOINICIO);
                    Nodo nodoFinal = findOrSaveNodo(row, GisCargaDefinition.NODOFINAL);
                    saveArcoTiempo(row,gisCarga,trayecto,tipoDia,nodoInicial,nodoFinal);
                }else{
                    break;
                }
            }
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveArcoTiempo(Row row,GisCarga gisCarga, Trayecto trayecto, TipoDiaDetalle tipoDia, Nodo nodoInicial, Nodo nodoFinal) {

        int distancia = Integer.parseInt(row.getCell( GisCargaDefinition.DISTANCIA ).getStringCellValue());
        int secuencia = Integer.parseInt(row.getCell( GisCargaDefinition.SECUENCIA ).getStringCellValue());
        int sentido = Integer.parseInt(row.getCell( GisCargaDefinition.SENTIDO ).getStringCellValue());
        int tipoArco = Integer.parseInt(row.getCell( GisCargaDefinition.TIPOARCO ).getStringCellValue());
        String horaDesde = row.getCell( GisCargaDefinition.HORADESDE ).getStringCellValue();
        String horaHasta = row.getCell( GisCargaDefinition.HORAHASTA).getStringCellValue();
        String tiempoMinimo = row.getCell( GisCargaDefinition.TIEMPOMINIMO ).getStringCellValue();
        String tiempoMaximo = row.getCell( GisCargaDefinition.TIEMPOMAXIMO ).getStringCellValue();
        String tiempoOptimo = row.getCell( GisCargaDefinition.TIEMPOOPTIMO ).getStringCellValue();

        ArcoTiempo arcoTiempo = new ArcoTiempo(
                sentido,secuencia,tipoArco,
                distancia,horaDesde,horaHasta,
                tiempoMinimo,tiempoMaximo,tiempoOptimo,
                gisCarga,trayecto,tipoDia,
                nodoInicial,nodoFinal
        );

        gisCargaService.addArcoTiempo( arcoTiempo );

    }



    private Nodo findOrSaveNodo(Row row, int nodoinicio) {
         String nodoNombre = row.getCell(nodoinicio).getStringCellValue();
         List<Nodo> nodos = nodoService.getNodo( nodoNombre );
        if( nodos.size() == 0 ){
            Nodo nodo = new Nodo(nodoNombre);
            nodoService.addNodo( nodo );
            return nodo;
        }
        return  nodos.get(0);
    }

    private TipoDiaDetalle findOrSaveTipoDia(Row row,String tipoDiaD) {
        String tipoDiaNombre = row.getCell(GisCargaDefinition.TIPODIA).getStringCellValue();
        List<TipoDiaDetalle> tipoDiaByDetalle =  tipoDiaService.getTipoDiaByDetalle( tipoDiaNombre );
        if( tipoDiaByDetalle.size() == 0 ){
            TipoDia tipoDia = tipoDiaService.getTipoDia( tipoDiaD );
            TipoDiaDetalle tipoDiaDetalle = new TipoDiaDetalle( tipoDiaNombre, tipoDia );
            tipoDiaService.addTipoDiaDetalle( tipoDiaDetalle );
            return tipoDiaDetalle;
        }
        return tipoDiaByDetalle.get(0);
    }

    public void copyFile(String fileName, InputStream in) {
        try {

            destination= destination+fileName;
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public ExcelReader getExcelReader() {
        return excelReader;
    }

    public void setExcelReader(ExcelReader excelReader) {
        this.excelReader = excelReader;
    }

    public GisCargaService getGisCargaService() {
        return gisCargaService;
    }

    public void setGisCargaService(GisCargaService gisCargaService) {
        this.gisCargaService = gisCargaService;
    }
}
