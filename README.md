[![Build Status](https://travis-ci.org/Blackdread/rest-filter.svg?branch=master)](https://travis-ci.org/Blackdread/rest-filter)
[![Coverage Status](https://coveralls.io/repos/github/Blackdread/rest-filter/badge.svg?branch=master)](https://coveralls.io/github/Blackdread/rest-filter?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.blackdread.lib/rest-filter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.blackdread.lib/rest-filter)

# rest-filter
Repository to separate filter/criteria classes to reduce dependencies (and spring related auto-configuration from jhipster)

It tries to make use of Spring Framework optional but filters already have some annotations from Spring.

Will add later more functionality.

If Spring Framework is used, it will added only in specific package and dependencies will be made optional (true) to limit impact if not used.
