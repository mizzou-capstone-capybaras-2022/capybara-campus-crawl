import { Building } from "src/services/crawl-api"

export type RouteParameters = {
    fromBuilding: Building
    toBuilding: Building
    avoidCrowds: boolean
    stopByFood: boolean
    preferIndoors: boolean
    pitstops: Array<Building>
}