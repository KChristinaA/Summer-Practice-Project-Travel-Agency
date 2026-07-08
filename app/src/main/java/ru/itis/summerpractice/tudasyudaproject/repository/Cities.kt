package ru.itis.summerpractice.tudasyudaproject.repository

import ru.itis.summerpractice.tudasyudaproject.model.CityData

object Cities {
    private val cities = listOf(
        CityData(
            name = "Москва",
            imageUrl = "https://moskultura.ru/wp-content/uploads/2025/04/mosity.jpg",
            population = 13_274_285,
            area = 2561.5f,
            description = "Москва — это город невероятных контрастов, где сказочные башни Кремля соседствуют с футуристическими небоскребами, а история оживает на уютных пешеходных переулках. Основанная в 1147 году Юрием Долгоруким, за более чем 875 лет она превратилась из небольшого княжеского городка в столицу великой империи. Это атмосфера величественных парков и современных набережных, где на каждом шагу можно встретить дружелюбных белок и уток, и энергия мегаполиса, которая заряжает на все 100%. Здесь каждый камень хранит память о великих событиях — от коронаций царей до триумфа 1812 года. Москва влюбляет в себя с первой прогулки, удивляет масштабами и заставляет возвращаться снова и снова!"
        ),
        CityData(
            name = "Санкт-Петербург",
            imageUrl = "https://cdn5.vedomosti.ru/image/2023/9m/1069yn/original-1avr.jpg",
            population = 5_652_922,
            area = 1439f
        ),
        CityData(
            name = "Казань",
            imageUrl = "https://7d9e88a8-f178-4098-bea5-48d960920605.selcdn.net/f03d4b3c-9615-48fd-affd-ed5ad2731a6a/-/format/auto/-/quality/smart_retina/-/stretch/off/-/resize/1900x/",
            population = 1_329_825,
            area = 614.2f
        ),
        CityData(
            name = "Сочи",
            imageUrl = "https://gs-sochi.ru/wp-content/uploads/2021/01/more-v-sochi-1024x576.jpg",
            population = 445_149,
            area = 176.8f
        )
    )

    fun getCities(): List<CityData>{
        return cities
    }
}