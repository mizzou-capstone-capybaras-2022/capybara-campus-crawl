import { Building } from "src/services/crawl-api"

export type RouteParameters = {
    fromBuilding: Building
    toBuilding: Building
    preferIndoors: boolean
    pitstops: Array<Building>
}