<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

    <jsp:include page="general.jsp" />
    
    <!-- Pharmacy js -->
    <script src="../js/imagePrevisualization.js"></script>
    <script>
    	imagePrevisualization($('#insertImg'), $('#insertImgViewer'));
    	imagePrevisualization($('#editImg'), $('#editImgViewer'));
    </script>