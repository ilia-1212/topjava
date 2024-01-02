<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
    if (sessionStorage.getItem('codeLang') === null) {
        lang = window.navigator.language.slice(0, 2);
        sessionStorage.setItem('codeLang', lang);
        window.location.href = window.location.toString() + "?lang=" + lang;
    }

    document.getElementById("dropDownLangMenuButton").innerHTML = sessionStorage.getItem('codeLang');

    function setLang(lang) {
        sessionStorage.setItem('codeLang', lang);
    }
</script>