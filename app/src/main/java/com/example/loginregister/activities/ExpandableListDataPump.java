package com.example.loginregister.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> Construction = new ArrayList<String>();
        Construction.add("Villas");
        Construction.add("Apartments");
        Construction.add("Duplex");
        Construction.add("Renovation");
        Construction.add("Modular kitchens");
        Construction.add("Others ");

        List<String> Designing = new ArrayList<String>();
        Designing.add("Interior designing ");
        Designing.add("Exterior designing");
        Designing.add("Plans and elevation of building");
        Designing.add("Structural components of building");
        Designing.add("Others");


        List<String> Iron = new ArrayList<String>();
        Iron.add("Gate");
        Iron.add("Window grills");
        Iron.add("Galvanised iron sheet roofing");
        Iron.add("Railing for stairs, balcony ");
        Iron.add("Others");

        List<String> carpentry = new ArrayList<String>();
        carpentry.add("Doors");
        carpentry.add("Windows");
        carpentry.add("Furnitures");
        carpentry.add("Panceiling");
        carpentry.add("Others");

        List<String> electrical = new ArrayList<String>();
        electrical.add("Wiring a building or a house, including fittings");
        electrical.add("Repairs in wiring and electrical fittings");
        electrical.add("Others");

        List<String> Plumbing = new ArrayList<String>();
        Plumbing.add("Fitting");
        Plumbing.add("Water convenience");
        Plumbing.add("Installation of plumbing components");
        Plumbing.add("Others");

        List<String> Housekeeping = new ArrayList<String>();
        Housekeeping.add("Pest control");
        Housekeeping.add("Cleaning rooms");
        Housekeeping.add("Home maintenance");
        Housekeeping.add("Laundry");
        Housekeeping.add("Safety & security");
        Housekeeping.add("Decorations");

        List<String> Painting = new ArrayList<String>();
        Painting.add("Interior painting");
        Painting.add("Exterior");
        Painting.add("Floor");
        Painting.add("Others");

        List<String> Flooring = new ArrayList<String>();
        Flooring.add("Laminate flooring");
        Flooring.add("Stone flooring");
        Flooring.add("Tile flooring");
        Flooring.add("Vinyl flooring");
        Flooring.add("Wooden flooring");
        Flooring.add("Glass flooring");
        Flooring.add("Marble flooring");
        Flooring.add("Parking flooring");
        Flooring.add("Industry flooring");
        Flooring.add("Pathway flooring");
        Flooring.add("Others");

        List<String>Manpower = new ArrayList<String>();
        Manpower.add("Maid");
        Manpower.add("Security guard");
        Manpower.add("Cook");
        Manpower.add("Driver");
        Manpower.add("Labour");
        Manpower.add("Mason");
        Manpower.add("Others");

        List<String>Landscaping = new ArrayList<String>();
        Landscaping.add("Lawn");
        Landscaping.add("Playground");
        Landscaping.add("Park");
        Landscaping.add("Industrial");
        Landscaping.add("Vertical gardening");
        Landscaping.add("Others");

        List<String>Ceiling = new ArrayList<String>();
        Ceiling.add("Gypsum ceiling");
        Ceiling.add("Plaster of paris(POP)");
        Ceiling.add("Fiber");
        Ceiling.add("Wooden");
        Ceiling.add("Glass");
        Ceiling.add("Metal");
        Ceiling.add("Leather and cloth");
        Ceiling.add("Heat resistant");

        List<String>servicing = new ArrayList<String>();
        servicing.add("AC servicing");
        servicing.add("Vehicle servicing");

        List<String>Packers= new ArrayList<String>();
        Packers.add("Others");

        List<String>Flowers= new ArrayList<String>();
        Flowers.add("Others");

        List<String>Wedding= new ArrayList<String>();
        Wedding.add("Others");

        List<String>Vehicle= new ArrayList<String>();
        Vehicle.add("Others");
        List<String>Health= new ArrayList<String>();
        Health.add("others");


        expandableListDetail.put("Construction", Construction);
        expandableListDetail.put("Designing", Designing);
        expandableListDetail.put("Iron and steel fabrications", Iron);
        expandableListDetail.put("Carpentry",carpentry);
        expandableListDetail.put("Electrical fittings and repairs",electrical);
        expandableListDetail.put("Plumbing",Plumbing);
        expandableListDetail.put("Housekeeping ",Housekeeping);
        expandableListDetail.put("Painting",Painting);
        expandableListDetail.put("Flooring",Flooring);

        expandableListDetail.put("Manpower services",Manpower);
        expandableListDetail.put("Landscaping services",Landscaping);
        expandableListDetail.put("Ceiling ",Ceiling);
        expandableListDetail.put("Servicing & Repair",servicing);
        expandableListDetail.put("Packers and movers ",Packers);
        expandableListDetail.put("Flowers & Gifts",Flowers);
        expandableListDetail.put("Wedding & Party",Wedding);
        expandableListDetail.put("Vehicle rental ",Vehicle);
        expandableListDetail.put("Health services",Health);


        return expandableListDetail;
    }
}