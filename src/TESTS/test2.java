package TESTS;
public class test2 extends test1 implements interace2 {
    int hi ;
    
    int hio ;
    
    int hi2;

    
    public void methodePublique1() {
        // Code ici
    }
    public void methodePublique1(int i) {
        // Code ici
    }
    public void methodePublique1(int i, double b) {
        // Code ici
    }

    public void methodePublique2() {
        // Code ici
    }
    public void methodePublique2(int i) {
        // Code ici
    }
    public void methodePublique2(double j) {
        // Code ici
    }

    // Méthode non publique
    private void methodePrivee() {
        // Code ici
    }
@Override
public String toString() {
    // TODO Auto-generated method stub
    return super.toString();
}

    
    public void test(){
        System.out.println("test2");
    }

    public int test10(){
        return 3;
    }
    public boolean test0(){
        return true; 
    }
    @Override
    public void interface0() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'interface0'");
    }

}
