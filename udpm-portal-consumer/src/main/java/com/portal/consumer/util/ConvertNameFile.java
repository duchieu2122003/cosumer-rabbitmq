package com.portal.consumer.util;

public class ConvertNameFile {

    public String covertFileName(String fileOld) {
        String chuoiKhongDau = removeDiacritics(fileOld);
        String chuoiChuThuong = chuoiKhongDau.toLowerCase();
        String output = chuoiChuThuong.replaceAll("[^a-zA-Z0-9]", "_");
        String fileNew = output.replace(" ", "_");

        return fileNew;
    }

    public String removeDiacritics(String text) {
        text = text.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a")
                .replaceAll("[èéẹẻẽêềếệểễ]", "e")
                .replaceAll("[ìíịỉĩ]", "i")
                .replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o")
                .replaceAll("[ùúụủũưừứựửữ]", "u")
                .replaceAll("[ỳýỵỷỹ]", "y")
                .replaceAll("[đ]", "d")
                .replaceAll("[ÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴ]", "A")
                .replaceAll("[ÈÉẸẺẼÊỀẾỆỂỄ]", "E")
                .replaceAll("[ÌÍỊỈĨ]", "I")
                .replaceAll("[ÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠ]", "O")
                .replaceAll("[ÙÚỤỦŨƯỪỨỰỬỮ]", "U")
                .replaceAll("[ỲÝỴỶỸ]", "Y")
                .replaceAll("[Đ]", "D");
        return text;
    }

}
