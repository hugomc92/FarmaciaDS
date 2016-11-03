<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

    <jsp:include page="general.jsp" />
    
    <!-- BootStrap table -->
    <script src="../assets/js/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="../assets/js/bootstrap-table/extensions/key-events/bootstrap-table-key-events.js"></script>
    <script src="../assets/js/bootstrap-table/extensions/mobile/bootstrap-table-mobile.js"></script>
    
    <!-- Product js -->
    <script src="../js/textParser.js"></script>
    <script src="../js/product.js"></script>
    <script src="../js/imagePrevisualization.js"></script>
    <script>
    	imagePrevisualization($('#insertImg'), $('#insertImgViewer'));
    	imagePrevisualization($('#editImg'), $('#editImgViewer'));
    </script>