/**
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


export interface RouteErrorResponse { 
    failingConstraint?: Array<RouteErrorResponse.FailingConstraintEnum>;
}
export namespace RouteErrorResponse {
    export type FailingConstraintEnum = 'food' | 'pitstop' | 'time' | 'crowds' | 'route';
    export const FailingConstraintEnum = {
        Food: 'food' as FailingConstraintEnum,
        Pitstop: 'pitstop' as FailingConstraintEnum,
        Time: 'time' as FailingConstraintEnum,
        Crowds: 'crowds' as FailingConstraintEnum,
        Route: 'route' as FailingConstraintEnum
    };
}


