package com.company;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import static com.sun.org.apache.xalan.internal.lib.ExsltStrings.split;

public class Main {
    static String inputFile;
    static Map<String, Object[]> datavalue;
    static String CHASIS = "";
    static String PHONE = "";
    static String ADDRESS="";
    static String NAME="";
    static String TEMPADDRESS="";
    public static void main(String[] args) throws IOException, JSONException {
        ArrayList<String> passwordList = new ArrayList();
        Process pp;
        Map<String, Object[]> data;
        passwordList.add("dona@123");
        passwordList.add("dona@123");
        passwordList.add("dona@facebook");
        passwordList.add("dona@123");
     //  read("C:\\Users\\admin1\\Downloads\\23.xls");
       // writeExcelFile();
       // readPDFInURL("https://smartweb.keralamvd.gov.in/kmvdnew/services/reports/eFeeReceipt421K287882017.pdf");
       // System.out.println(ADDRESS);
        int i=2,sno=1;
        try
        {
            FileInputStream x = new FileInputStream(new File("C:\\Users\\admin1\\Downloads\\23.xls"));

            //Create Workbook instance holding reference to .xlsx file
            Workbook workbook = new HSSFWorkbook(x);

            //Get first/desired sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);
            data = new HashMap<String, Object[]>();
            data.put("1", new Object[] {"sno","","CHASIS.", "PHONE", "ADDRESS","NAME"});
            //Iterate through each rows one by one
            for (Iterator<Row> iterator = sheet.iterator(); iterator.hasNext();) {
                Row row = iterator.next();
                for (Iterator<Cell> iterator2 = row.iterator(); iterator2
                        .hasNext();) {
                    Cell cell = (Cell) iterator2.next();
                    if (!cell.getStringCellValue().equals(""))
                    System.out.println(cell.getStringCellValue().trim().replace("/", ""));
                   readPDFInURL("https://smartweb.keralamvd.gov.in/kmvdnew/services/reports/eFeeReceipt"+cell.getStringCellValue().trim().replace("/", "")+".pdf");
                //    System.out.print(CHASIS+"\n"+PHONE+"\n"+ADDRESS+"\n"+NAME);

                    data.put(String.valueOf(i++), new Object[] {sno++,cell.getStringCellValue().trim(), CHASIS, PHONE,ADDRESS,NAME});
                }
            }
            x.close();

          writeExcelFile(data);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    //  readPDFInURL("https://smartweb.keralamvd.gov.in/kmvdnew/services/reports/eFeeReceipt421K331352017.pdf");
/*        for(String pass : passwordList) {
            System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
            InetAddress inetAddress = null;
            try {
                inetAddress = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            System.out.println(inetAddress.getHostAddress());
            String proxy = "200.37.54.10:57040";
            ChromeOptions options = new ChromeOptions().addArguments("--proxy-server=http://" + proxy);
            WebDriver driver = new ChromeDriver();
            String url = "https://smartweb.keralamvd.gov.in/kmvdnew/services/reports/eFeeReceipt421K331352017.pdf";
            driver.get(url);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);*/
           /* WebElement email_phone = driver.findElement(By.xpath("//input[@id='email']"));
            email_phone.sendKeys("donarichard007");
            //driver.findElement(By.id("identifierNext")).click();
            WebElement password = driver.findElement(By.xpath("//input[@id='pass']"));
            WebDriverWait wait = new WebDriverWait(driver, 20);
            wait.until(ExpectedConditions.elementToBeClickable(password));
            password.sendKeys(pass);
            driver.findElement(By.id("loginbutton")).click();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String title = "Facebook";
            if (driver.getTitle().equals(title)) {
                WebElement logoutProfile = driver.findElement(By.xpath("//a[contains(@class, 'gb_x gb_Da gb_f')]"));
                WebElement logoutButton = driver.findElement(By.xpath("//a[contains(@class, 'gb_0 gb_Xf gb_5f gb_Be gb_gb')]"));
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", logoutProfile);
                executor.executeScript("arguments[0].click();", logoutButton);
                driver.close();
              break;
            } else {
                // System.out.println("Unable to loggin :-( "+driver.getTitle());
                driver.close();
            }*/
        }
    public static void readPDFInURL(String url) throws IOException, JSONException {
        String output,temp1 = "",temp2 = "";
        JSONArray jsonArray;
        String line = null;
        ArrayList data=new ArrayList();
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        // page with example pdf document

        driver.get(url);

        URL Fileurl = new URL(driver.getCurrentUrl());

        InputStream is = Fileurl.openStream();

        BufferedInputStream fileToParse = new BufferedInputStream(is);

        PDDocument document = null;

        try {

            document = PDDocument.load(fileToParse);
            output = new PDFTextStripper().getText(document);
            Scanner scanner = new Scanner(output);
            while (scanner.hasNextLine()) {
                int sno=1;
               line = scanner.nextLine();
                if (line.contains("Name")){
                    String part = line.split("[\\.:]")[1].trim();;
                  //  System.out.println("\n"+part);
                    NAME=part;
                }
                if (line.contains("Address"))
                {
                    /*String part = line.split("[\\.:]")[1].trim();;*/
                    String address=line;
                  String currectAddress= address.replaceAll(".*:", "").trim();
                    while (scanner.hasNextLine()) {
                        line=scanner.nextLine();
                        if (line.contains("-"))
                        { line.split("-");
                            /*String part = line.split("[\\.:]")[1].trim();;*/
                            //  System.out.println("\n"+line);
                            String str = line;
                            str = str.replaceAll("[^?0-9]+", " ").trim();
                            String last10 = str.replaceAll(".*?(.?.?.?.?.?.?.?.?.?.?)?$", "$1");
                            PHONE= last10;
                            System.out.println(PHONE);
                        }

                        if (!line.contains("Chassis No")) {
                            temp1=temp1.concat(line).trim();
                            //    ADDRESS = line;
                            //    System.out.println("\t"+address.concat(ADDRESS))
                        } else if(line.contains("Chassis No")){
                            String part = line.split("[\\.:]")[1].trim();
                        String chassisNo=    part.replaceAll(" .*", "");
                            String last5 = chassisNo.replaceAll(".*?(.?.?.?.?.)?$", "$1");
                            CHASIS= last5;
                            break;
                        }
                    }

                    ADDRESS=currectAddress.concat(temp1);

                }
                if (line.contains("Chassis No"))
               {

                   break;
               }
            }
            scanner.close();
        } finally {

            if (document != null) {

                document.close();

            }

            fileToParse.close();

            is.close();

        }
driver.close();
    }

    private static String writeExcelFile(Map<String, Object[]> data){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sample sheet");
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
                else if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Double)
                    cell.setCellValue((Double)obj);
            }
        }

        try {
            //new excel file created by fileoutput stream object
            FileOutputStream out =
                    new FileOutputStream(new File("C:\\Users\\admin1\\Desktop\\output_File..xls"));
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "file reading is completed";
    }
    }
