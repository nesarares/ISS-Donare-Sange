package donation.model;

import java.io.Serializable;


public enum DonationStatus implements Serializable {
    Taking,
    Preparation,
    Classification,
    Distribution
}