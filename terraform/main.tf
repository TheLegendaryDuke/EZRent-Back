provider "google" {
  credentials = "${file("terraformAccount.json")}"
  project = "ezrent-184918"
  region = "us-central1"
}

resource "google_compute_instance" "ezrent" {
  name = "ezrent"
  machine_type = "f1-micro"
  zone = "us-central1-c"
  allow_stopping_for_update = true

  tags = ["sql-in", "http-server", "https-server"]

  boot_disk {
    initialize_params {
      image = "projects/gce-uefi-images/global/images/cos-69-10895-71-0"
    }
  }

  metadata_startup_script = "docker run --name postgres11 -e POSTGRES_PASSWORD=112358 -d postgres"

  network_interface {
    network = "default"
    access_config {
      public_ptr_domain_name = "aoranzhang.com"
    }
  }
}