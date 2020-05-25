<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]


<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a>
    <img src="https://clipartix.com/wp-content/uploads/2016/04/Train-clipart-for-kids-free-free-clipart-images.gif" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">Commute Status Web App</h3>

  <p align="center">
    Commute Status a simple Web and Android app to quickly see the status of your commute.
    <br />
    <br />
    <a href="https://commutestatus.suskins.co.uk">Check it out!</a>
    ·
    <a href="https://github.com/BenSuskins/commutestatus-web-app/issues">Report Bug</a>
    ·
    <a href="https://github.com/BenSuskins/commutestatus-web-app/issues">Request Feature</a>
  </p>
</p>




<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Roadmap](#roadmap)
* [Contributing](#contributing)
* [License](#license)
* [Contact](#contact)
* [Acknowledgements](#acknowledgements)



<!-- ABOUT THE PROJECT -->
## About The Project

![Commute Status Screenshot](docs/commuteStatus.png)

Commute Status is a simple Web and Android app to quickly and easily see the status of your commute.

Check out the [Android App](https://github.com/BenSuskins/commutestatus-android-app).

Check out the [Web App](https://github.com/BenSuskins/commutestatus-web-app).


### Built With

* Java 
* Spring Boot
* National Rail Darwin API
* Auth0
* Postgres



<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* Maven
```sh
mvn install
```

#### Environment Variables
* AUTH0_CLIENT_ID
* AUTH0_CLIENT_SECRET
* AUTH0_CLIENT_SECRET
* AUTH0_REFRESH_SECRET
* AUTH0_DOMAIN
* NATIONAL_RAIL_ACCESS_TOKEN
* POSTGRES_HOST
* POSTGRES_PORT
* POSTGRES_DATABASE
* POSTGRES_USERNAME
* POSTGRES_PASSWORD

### Installation
 
1. Clone the commutestatus-api
```sh
git clone https://github.com/BenSuskins/commutestatus-api.git
```
2. Install Maven dependencies
```sh
mvn install
```

2. Run application
```sh
mvn spring-boot:run
```

<!-- ROADMAP -->
## Roadmap

See the [open issues](https://github.com/BenSuskins/commutestatus-api/issues) for a list of proposed features (and known issues).



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.



<!-- CONTACT -->
## Contact

Ben Suskins - [@BenSuskins](https://twitter.com/BenSuskins) - suskinsdevelopment@gmail.com

Project Link: [https://github.com/BenSuskins/commutestatus-api](https://github.com/BenSuskins/commutestatus-api)



<!-- ACKNOWLEDGEMENTS -->
## Acknowledgements

* []()
* []()
* []()





<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[issues-shield]: https://img.shields.io/badge/Issues-0-brightgreen
[issues-url]: https://github.com/BenSuskins/commutestatus-api/issues
[license-shield]: https://img.shields.io/badge/License-MIT-brightgreen
[license-url]: https://github.com/BenSuskins/commutestatus-api/blob/master/LICENSE.txt
[product-screenshot]: images/screenshot.png
