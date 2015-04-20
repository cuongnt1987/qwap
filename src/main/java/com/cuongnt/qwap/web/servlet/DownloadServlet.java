/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.servlet;

import com.cuongnt.qwap.ejb.AppFileService;
import com.cuongnt.qwap.ejb.ImageFileService;
import com.cuongnt.qwap.entity.File;
import com.cuongnt.qwap.util.AppConfig;
import java.io.IOException;
import java.io.OutputStream;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richard
 */
@WebServlet(name = "DownloadServlet", urlPatterns = {"/download/*"})
public class DownloadServlet extends HttpServlet {

    private static final long serialVersionUID = 2701319319520136275L;
    private static final Logger logger = LoggerFactory.getLogger(DownloadServlet.class);
    public static final String IMAGE_PATH = "image";
    public static final String APP_PATH = "app";
    public static final String INLINE_PATH = "inline";
    
    @EJB
    private ImageFileService imageFileService;
    @EJB
    private AppFileService appFileService;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        if (request.getPathInfo() != null) {
            String[] paths = request.getPathInfo().split("/");
            // TODO: finish this function
            File file = null;
            boolean inline = false;
            switch (paths[1]) {
                case IMAGE_PATH:
                    if (paths[2].equals(INLINE_PATH) && paths.length > 3) {
                        inline = true;
                        file = imageFileService.find(Long.parseLong(paths[3]));
                    } else if (paths.length > 2) {
                        file = imageFileService.find(Long.parseLong(paths[2]));
                    }
                    
                    break;
                case APP_PATH:
                    if (paths[2].equals(INLINE_PATH) && paths.length > 3) {
                        inline = true;
                        file = appFileService.find(Long.parseLong(paths[3]));
                    } else if (paths.length > 2) {
                        file = appFileService.find(Long.parseLong(paths[2]));
                    }
                    break;
                default:
                    break;

            }
            if (file != null && file.getTitle() != null) {
                response.setContentLength((int) file.getFileSize());
                response.setContentType(file.getContentType());
                response.setHeader("Content-Disposition", (inline ? "inline" : "attachment")
                        + ";filename=\"" + file.getTitle() + "\"");
                
                // write file to response
                java.io.File ioFile = new java.io.File(AppConfig.getFileStorePath() + file.getId() + java.io.File.separator + file.getTitle());
                try (OutputStream out = response.getOutputStream()) {
                    FileUtils.copyFile(ioFile, out);
                } catch (IOException e) {
                    logger.error("Error when download file: fileId =" + file.getId(), e);
                } 
            }

        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Download file servlet.";
    }// </editor-fold>

}
