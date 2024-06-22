<a name="readme-top"></a>

<a href=".gitassets/README-RU.md">RU</a> | <a>EN</a>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://donntu.ru/" target="_blank">
    <img src="https://donntu.ru/sites/default/files/images/gerb_donntu_large.jpg" height="150px">
  </a>

  <h3 align="center">Fuzzy-Output*</h3>

  <p align="center">
    A control system based on the principles of a fuzzy set
    <br />
    <a href="#"><strong>Description »</strong></a> ▪
    <a href="#"><strong>Development »</strong></a> ▪
    <a href="#"><strong>Testing »</strong></a>
    <br />
    <br />
  </p>
  <p align="right">
    <small><i>*The project was carried out within the framework of the discipline course "Fuzzy methods of data processing and analysis" in the course of study at</br><strong> Donetsk National Technical University</strong></i></small>
  </p>
</div>


## About

![Program screenshot](.gitassets/third-testing-set.png)

This project is a course work on the topic "Management system based on the principles of fuzzy set". It was carried out in the course of master's degree in FSBEI HE DonNTU in 2023. The purpose of the coursework was to master the basic concepts and principles of building decision-making systems using fuzzy logic, the study of basic design methodologies and ways to implement such systems.

The main task of this course work was to develop a system that allows to analyse the server input data in real time and make informed decisions based on the implemented fuzzy logic algorithms.

The system was implemented using the Java programming language and the JavaFX library for creating user interfaces.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


## Getting started

To start a local copy of the system, follow these simple steps.

### Components

Make sure you have the following components installed:

* Oracle Java 11
* Apache Maven 3.9.8

### Installing

1. Clone the repository
  ```sh
    git clone https://github.com/sabitovka/fuzzy-output
  ```
2. Compile the project using Maven
  ```sh
    mvn clean compile
  ```
3. Run the system
  ```sh
    mvn javafx:run
  ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>


## Usage

The developed system is designed to set the CPU frequency on the basis of fuzzy rules of the intensity of accesses to the server and the amount of remaining RAM. Information about the current number of accesses to the server and the intensity of accesses is entered on the form, and then the recommended CPU frequency is displayed

![Example of usage](.gitassets/second-testing-set.png)

Fuzzy rules, which are represented by terms of linguistic variables and membership functions, can be edited. For this purpose the settings form is used. Each linguistic variable can be extended with a set of terms. Each term can be set to one of the following membership functions:

* Quadratic
* Linear
* Exponential

![Форма настроек](.gitassets/settings-form-1.png)

_More usage examples are described in the [Testing](#) section_

<p align="right">(<a href="#readme-top">back to top</a>)</p>


## Contribution

Contributions are what make the open source community a great place to learn, inspire and create. I **highly appreciate any contribution you make**.

If you have suggestions for improving the system, make a Fork and create a Merge Request. You can also create an issue with the tag "enhancement"

1. Fork the project
2. Create a new branch of the feature (`git checkout -b feature/your-feature`)
3. Commit the changes (`git commit -m 'Added new feature'`)
4. Push the changes (`git push origin feature/your-feature`)
5. Create a merge request

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Licence

Distributed under the MIT licence. See the file "LICENSE.txt" for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Contacts

Karim Sabitov
* [VK](https://vk.com/id87074050)
* [karim.sab@yandex.ru](mailto://karim.sab@yandex.ru)

Other links to the project:
* GitHub - [https://github.com/sabitovka/fuzzy-output](https://github.com/sabitovka/fuzzy-output)
* GitFlic - [https://gitflic.ru/project/sabitovka/fuzzy-output](https://gitflic.ru/project/sabitovka/fuzzy-output)

<p align="right">(<a href="#readme-top">back to top</a>)</p>