CactusXMLParser
===============

Create instance for one class, all public fields will be set refer to a XML file
Rule: field name should be the tag name in XML, and for ArrayList there should be multi tag named with same field name

Example

=====Class=====
public class Tar
{
  int a;
  boolen b;
  Inner c;
  ArrayList<Inner> d;

}

public class Inner
{
  String c1;
  ArrayList<Integer> c2;
}

=====XML======
<Configure>
  <a>99</a>
  <b>true</b>
  <c>
    <c1>for c.c1</c1>
    <c2>1</c2>
    <c2>2</c2>
    <c2>3</c2>
  </c>
  <d>
    <c1>for d_1.c1</c1>
    <c2>11</c2>
    <c2>12</c2>
    <c2>13</c2>
  </d>
  <d>
    <c1>for d_2.c1</c1>
    <c2>21</c2>
    <c2>22</c2>
    <c2>23</c2>
  </d>
  <d>
    <c1>for d_3.c1</c1>
    <c2>31</c2>
    <c2>32</c2>
    <c2>33</c2>
  </d>
</Configure>




