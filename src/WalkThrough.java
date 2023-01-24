import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class WalkThrough {
    public static void main(String[] args) {
        ArrayList<Room> rooms = new ArrayList<>();
        try {
            InputStream is = WalkThrough.class.getClassLoader().getResourceAsStream("Rooms.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(false);
            Document doc = dbf.newDocumentBuilder().parse(is);

            XPathFactory xf = XPathFactory.newInstance();
            XPath xPath = xf.newXPath();

            // Find Room anywhere within the document...
            XPathExpression xExp = xPath.compile("//room");
            NodeList nl = (NodeList) xExp.evaluate(doc, XPathConstants.NODESET);

            int totalDoors = 0;
            for (int ri = 0; ri < nl.getLength(); ri++) {
                Node node = nl.item(ri);
                NamedNodeMap nnm = node.getAttributes();
                int ridx =Integer.parseInt(nnm.getNamedItem("id").getTextContent());

                System.out.printf("room %d name %s%n", ridx, nnm.getNamedItem("name").getTextContent());

                rooms.add(ridx, new Room(nnm.getNamedItem("name").getTextContent()));
                NodeList doorNodes = node.getChildNodes();
                for (int di = 0; di < doorNodes.getLength(); di++) {
                    Node doorNode = doorNodes.item(di);
                    NamedNodeMap dnnm = doorNode.getAttributes();
                    if(dnnm != null) { // Not yet sure why we sometimes get a null here.
                        int doorId = Integer.parseInt(dnnm.getNamedItem("id").getTextContent());
                        int connectIdx = Integer.parseInt(dnnm.getNamedItem("connects").getTextContent());

                        System.out.printf("  door %d leads to room %s%n", doorId, rooms.get(connectIdx).toString());

                        Door d = new Door(doorId, false);
                        totalDoors++;
                        rooms.get(ridx).connect(rooms.get(connectIdx), d);
                    }
                }
            }

            for(Room r : rooms) {
                System.out.printf("Starting walk in room %s with %d unlocked doors%n", r, r.getUnlockedDoors().size());
                walk(r, new ArrayList<>(), totalDoors);
            }

        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException exp) {
            exp.printStackTrace();
        }
    }

    public static void walk(Room current, ArrayList<Door> path, int totalDoors) {
        if(current.getUnlockedDoors().size() == 0) {
            //System.out.println(path);
            if(path.size() >= totalDoors) {
                System.out.println(path);
            }
            return;
        }
        for(Door d : current.getUnlockedDoors()) {
            Room r = d.goThrough(current);
            d.setLocked(true); // Lock it before recursive call.
            path.add(d);
            walk(r, path, totalDoors);
            path.remove(d);
            d.setLocked(false); // Unlock it on the way back.
        }
    }
}
