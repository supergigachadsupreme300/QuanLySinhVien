public class GuiInitTest {
    public static void main(String[] args) {
        String[] forms = new String[]{
            "GUI.FormGiaoVien",
            "GUI.FormMonHoc",
            "GUI.FormChiTietMon",
            "GUI.FormNamHoc",
            "GUI.FormHocKy",
            "GUI.RunFormPhuHuynh",
            "GUI.RunFormHocSinh",
            "GUI.FormReport",
            "GUI.MainMenu",
            "GUI.parent_GUI",
            "GUI.student_GUI",
            "GUI.TietHoc"
        };
        for (String fqcn : forms) {
            System.out.println("Instantiating: " + fqcn);
            try {
                Class<?> cls = Class.forName(fqcn);
                try {
                    Object obj = cls.getDeclaredConstructor().newInstance();
                    System.out.println(" OK: " + fqcn + " instanciated: " + obj.getClass().getName());
                } catch (NoSuchMethodException nsme) {
                    System.out.println(" WARN: no default constructor for " + fqcn);
                } catch (Throwable t) {
                    System.out.println(" ERROR constructing " + fqcn + ": " + t);
                    t.printStackTrace(System.out);
                }
            } catch (ClassNotFoundException e) {
                System.out.println(" MISSING: " + fqcn);
            }
        }
        System.out.println("GuiInitTest done.");
    }
}
